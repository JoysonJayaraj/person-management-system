package com.jsp.pms.services.repositories;

import com.jsp.pms.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person , Integer> {

    @Query(value = "from Person where age = :page")
    List<Person> findByAge(@Param(value = "page") Integer personAge);
}
