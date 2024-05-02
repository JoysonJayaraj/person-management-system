package com.jsp.pms.services.servicesImpl;

import com.jsp.pms.dto.PersonBasicDTO;
import com.jsp.pms.entities.Person;
import com.jsp.pms.services.PersonService;
import com.jsp.pms.services.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceImplTest {

    @InjectMocks
    PersonServiceImpl personServiceImpl;

    @Mock
    PersonRepository personRepository;

    @Test
    public void getPersonTest() {
        when(personRepository.findById(1)).thenReturn(createPersonStub());
        PersonBasicDTO personTest = personServiceImpl.getPerson(1);
        Assertions.assertEquals(personTest.getPersonName(), "Puran");
    }

    private Optional<Person> createPersonStub() {
        Person studPerson = Person.builder().personId(1).personName("Puran").age(24).emailId("puran@gmail.com").build();
        return Optional.of(studPerson);

    }
}
