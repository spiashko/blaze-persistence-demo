package com.spiashko.blazepersistencedemo.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.EntityViewId;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.blazebit.text.FormatUtils;
import com.blazebit.text.ParserContext;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.config.BlazeFilterQueryParam;
import com.spiashko.blazepersistencedemo.config.BlazePageableAsQueryParam;
import com.spiashko.blazepersistencedemo.filter.Filter;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.repository.CatPageRepository;
import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView_;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatWithOwnerView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class CatRestController {

    private static final Map<String, SerializableFormat<?>> FILTER_ATTRIBUTES;

    static {
        Map<String, SerializableFormat<?>> filterAttributes = new HashMap<>();
        filterAttributes.put(CatSimpleView_.ID, FormatUtils.getAvailableFormatters().get(Long.class));
        filterAttributes.put(CatSimpleView_.NAME, FormatUtils.getAvailableFormatters().get(String.class));
        filterAttributes.put(CatSimpleView_.DOB, FormatUtils.getAvailableFormatters().get(LocalDate.class));
        filterAttributes.put(CatSimpleView_.OWNER_ID, FormatUtils.getAvailableFormatters().get(Long.class));
        FILTER_ATTRIBUTES = Collections.unmodifiableMap(filterAttributes);
    }

    private final CatRepository repository;
    private final CatPageRepository pageRepository;

    @RequestMapping(path = "/cats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView create(@RequestBody CatCreateView catCreateView) {
        CatCreateView cat = repository.save(catCreateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @Parameter(in = ParameterIn.PATH
            , name = "id"
            , content = @Content(schema = @Schema(type = "integer")))
    @RequestMapping(path = "/cats/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView updateCat(@EntityViewId("id") @RequestBody CatUpdateView catUpdateView) {
        CatUpdateView cat = repository.save(catUpdateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @RequestMapping(path = "/cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAll() {
        List<CatSimpleView> result = repository.findAll(CatSimpleView.class);
        return result;
    }

    @BlazeFilterQueryParam
    @BlazePageableAsQueryParam
    @RequestMapping(path = "/pageable-cats", method = RequestMethod.GET)
    public Page<CatSimpleView> findAllPageable(
            @Parameter(hidden = true) @KeysetConfig(Cat.class) KeysetPageable keysetPageable,
            @Parameter(hidden = true) @RequestParam(name = "filter", required = false) final Filter[] filter
    ) {
        Specification<CatSimpleView> specification = getSpecificationForFilter(filter);
        Page<CatSimpleView> result = pageRepository.findAll(specification, keysetPageable);
        return result;
    }

    @RequestMapping(path = "/cats/{id}", method = RequestMethod.GET)
    public CatWithOwnerView findById(@PathVariable Long id) {
        CatWithOwnerView result = repository.findById(id, CatWithOwnerView.class);
        return result;
    }

    private Specification<CatSimpleView> getSpecificationForFilter(final Filter[] filter) {
        if (filter == null || filter.length == 0) {
            return null;
        }
        return (Specification<CatSimpleView>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParserContext parserContext = new ParserContextImpl();
            try {
                for (Filter f : filter) {
                    SerializableFormat<?> format = FILTER_ATTRIBUTES.get(f.getField());
                    if (format != null) {
                        String[] fieldParts = f.getField().split("\\.");
                        Path<?> path = root.get(fieldParts[0]);
                        for (int i = 1; i < fieldParts.length; i++) {
                            path = path.get(fieldParts[i]);
                        }
                        switch (f.getKind()) {
                            case EQ:
                                predicates.add(criteriaBuilder.equal(path, format.parse(f.getValue(), parserContext)));
                                break;
                            case GT:
                                predicates.add(criteriaBuilder.greaterThan((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case LT:
                                predicates.add(criteriaBuilder.lessThan((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case GTE:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case LTE:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case IN:
                                List<String> values = f.getValues();
                                List<Object> filterValues = new ArrayList<>(values.size());
                                for (String value : values) {
                                    filterValues.add(format.parse(value, parserContext));
                                }
                                predicates.add(path.in(filterValues));
                                break;
                            case BETWEEN:
                                predicates.add(criteriaBuilder.between((Expression<Comparable>) path, (Comparable) format.parse(f.getLow(), parserContext), (Comparable) format.parse(f.getHigh(), parserContext)));
                                break;
                            case STARTS_WITH:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, format.parse(f.getValue(), parserContext) + "%"));
                                break;
                            case ENDS_WITH:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, "%" + format.parse(f.getValue(), parserContext)));
                                break;
                            case CONTAINS:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, "%" + format.parse(f.getValue(), parserContext) + "%"));
                                break;
                            default:
                                throw new UnsupportedOperationException("Unsupported kind: " + f.getKind());
                        }
                    }
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private static class ParserContextImpl implements ParserContext {
        private final Map<String, Object> contextMap;

        private ParserContextImpl() {
            this.contextMap = new HashMap();
        }

        public Object getAttribute(String name) {
            return this.contextMap.get(name);
        }

        public void setAttribute(String name, Object value) {
            this.contextMap.put(name, value);
        }
    }

}
