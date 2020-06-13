package com.spiashko.blazepersistencedemo;

import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.model.Person;
import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class BlazePersistenceDemoApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CatRepository catRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlazePersistenceDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        catRepository.deleteAll();
        personRepository.deleteAll();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Person p = new Person("Person " + i);
            people.add(p);
            personRepository.save(p);
        }
        for (int i = 0; i < 100; i++) {
            long minDay = LocalDate.of(2010, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2020, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            Cat c = new Cat("Cat " + i, randomDate, people.get(random.nextInt(4)));
            catRepository.save(c);
        }
    }

}
