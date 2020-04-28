package com.spiashko.blazepersistencedemo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_owner")
    private Person owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_mother")
    private Cat mother;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_father")
    private Cat father;

}
