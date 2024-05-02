package com.jsp.pms.services.servicesImpl;

import com.jsp.pms.dto.PersonBasicDTO;
import com.jsp.pms.dto.PersonRegistrationDTO;
import com.jsp.pms.entities.Person;
import com.jsp.pms.exception.PersonNotFoundException;
import com.jsp.pms.response.PageResponse;
import com.jsp.pms.services.PersonService;
import com.jsp.pms.services.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Integer savePerson(PersonRegistrationDTO personRegistrationDTO) {

        Person person = Person.builder()
                .personName(personRegistrationDTO.getPersonName())
                .age(personRegistrationDTO.getAge())
                .emailId(personRegistrationDTO.getEmailId())
                .build();

        personRepository.save(person);
        return person.getPersonId();
    }

    @Override
    public PersonBasicDTO getPerson(Integer personId) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person Not Found"));

        return PersonBasicDTO.builder()
                .personId(person.getPersonId())
                .personName(person.getPersonName())
                .age(person.getAge())
                .emailId(person.getEmailId())
                .build();

    }

    @Override
    public List<PersonBasicDTO> getAllPersons() {
        List<Person> all = personRepository.findAll();

        List<PersonBasicDTO> personBasicDTOS = new ArrayList<>();

        for(Person person : all) {
            personBasicDTOS.add(
                    PersonBasicDTO.builder()
                            .personId(person.getPersonId())
                            .personName(person.getPersonName())
                            .age(person.getAge())
                            .emailId(person.getEmailId())
                            .build()
            );
        }
        return personBasicDTOS;
    }



    @Override
    public boolean updatePerson(Integer personId, PersonRegistrationDTO personRegistrationDTO) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person Not Found"));

        person.setPersonName(personRegistrationDTO.getPersonName());
        person.setAge(personRegistrationDTO.getAge());
        person.setEmailId(personRegistrationDTO.getEmailId());

        personRepository.save(person);

        return true;
    }

    @Override
    public Integer deletePerson(Integer personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person Not Found"));

        personRepository.delete(person);
        return person.getPersonId();
    }

    @Override
    public List<PersonBasicDTO> getAllPersonsBasedOnAge(Integer personAge) {
        List<Person> all = personRepository.findByAge(personAge);

        List<PersonBasicDTO> personBasicDTOS = new ArrayList<>();

        for (Person person : all) {
            personBasicDTOS.add(
                    PersonBasicDTO.builder()
                            .personId(person.getPersonId())
                            .personName(person.getPersonName())
                            .age(person.getAge())
                            .emailId(person.getEmailId())
                            .build()
            );
        }
        return personBasicDTOS;
    }

    @Override
    public List<PersonBasicDTO> getAllPersonsByPagenationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

//        int pageSize = 5;
//        int pageNumber = 1;

//        Sort sort = null;
//
//        if(sortDir.equalsIgnoreCase("asc")) {
//            sort = Sort.by(sortBy).ascending();
//        } else {
//            sort = Sort.by(sortBy).descending();
//        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Person> pagePost = personRepository.findAll(p);

        List<Person> all = pagePost.getContent();

        List<PersonBasicDTO> personBasicDTOS = new ArrayList<>();

        for(Person person : all) {
            personBasicDTOS.add(
                    PersonBasicDTO.builder()
                            .personId(person.getPersonId())
                            .personName(person.getPersonName())
                            .age(person.getAge())
                            .emailId(person.getEmailId())
                            .build()
            );
        }

        return personBasicDTOS;
    }

    @Override
    public PageResponse getAllPersonsByPagenationAndSortingPageInfo(Integer pageNumber, Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);

        Page<Person> pagePost = personRepository.findAll(p);

        List<Person> all = pagePost.getContent();

        List<PersonBasicDTO> personBasicDTOS = new ArrayList<>();

        for(Person person : all) {
            personBasicDTOS.add(
                    PersonBasicDTO.builder()
                            .personId(person.getPersonId())
                            .personName(person.getPersonName())
                            .age(person.getAge())
                            .emailId(person.getEmailId())
                            .build()
            );
        }

        PageResponse pageResponse = new PageResponse();

        pageResponse.setContent(personBasicDTOS);
        pageResponse.setPageNumber(pagePost.getNumber());
        pageResponse.setPageSize(pagePost.getSize());
        pageResponse.setTotalElements(pagePost.getTotalElements());
        pageResponse.setTotalPages(pagePost.getTotalPages());

        pageResponse.setLastPage(pagePost.isLast());

        return pageResponse;
    }
}