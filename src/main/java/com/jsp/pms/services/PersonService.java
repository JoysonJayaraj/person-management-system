package com.jsp.pms.services;

import com.jsp.pms.dto.PersonBasicDTO;
import com.jsp.pms.dto.PersonRegistrationDTO;
import com.jsp.pms.response.PageResponse;

import java.util.List;

public interface PersonService {

    Integer savePerson(PersonRegistrationDTO personRegistrationDTO);

    PersonBasicDTO getPerson(Integer personId);

    List<PersonBasicDTO> getAllPersons();

    boolean updatePerson(Integer personId, PersonRegistrationDTO personRegistrationDTO);


    Integer deletePerson(Integer personId);

    List<PersonBasicDTO> getAllPersonsBasedOnAge(Integer personAge);

    List<PersonBasicDTO> getAllPersonsByPagenationAndSorting(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);

    PageResponse getAllPersonsByPagenationAndSortingPageInfo(Integer pageNumber, Integer pageSize);
}
