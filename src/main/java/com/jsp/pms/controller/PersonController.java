package com.jsp.pms.controller;

import com.jsp.pms.dto.PersonBasicDTO;
import com.jsp.pms.dto.PersonRegistrationDTO;
import com.jsp.pms.response.PageResponse;
import com.jsp.pms.response.SuccessResponse;
import com.jsp.pms.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(path = "api/persons")
@RestController
public class PersonController {

    private final PersonService personService;

    @GetMapping(path = "dummy")
    public PersonRegistrationDTO dummy() {
        return PersonRegistrationDTO.builder()
                .personName("Dalma")
                .age(25)
                .emailId("dalma@gmail.com")
                .build();
    }


    @PostMapping(path = "")
    public ResponseEntity<SuccessResponse> savePerson(@RequestBody PersonRegistrationDTO personRegistrationDTO) {

        Integer personId = personService.savePerson(personRegistrationDTO);

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Person Data Saved")
                        .status(HttpStatus.OK)
                        .data(personRegistrationDTO)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping(path = "person")
    public ResponseEntity<SuccessResponse> getPerson(@RequestParam(name = "pid") Integer personId) {

        PersonBasicDTO person = personService.getPerson(personId);

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Person Data Provided")
                        .data(person)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @GetMapping(path = "")
    public ResponseEntity<SuccessResponse> getAllPersons() {

        List<PersonBasicDTO> personBasicDTOS = personService.getAllPersons();

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(personBasicDTOS)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(path = "pages")
    public ResponseEntity<SuccessResponse> getAllPersonsByPagenationAndSorting(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "personId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
            ) {

        List<PersonBasicDTO> allPersonsByPagenationAndSorting = personService.getAllPersonsByPagenationAndSorting(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(allPersonsByPagenationAndSorting)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());


    }

    @GetMapping(path = "pages/page-info")
    public ResponseEntity<PageResponse> getAllPersonsByPagenationAndSortingAllPageInfo(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
    ) {

        PageResponse pageResponse = personService.getAllPersonsByPagenationAndSortingPageInfo(pageNumber, pageSize);

        return new ResponseEntity<PageResponse>(pageResponse,HttpStatus.OK);


    }

    @GetMapping(path = "duplicates")
    public ResponseEntity<SuccessResponse> getAllPersonsDuplicates() {

        List<PersonBasicDTO> all = personService.getAllPersons();

        List<String> namesList = all.stream().map(person -> person.getPersonName()).collect(Collectors.toList());
        Set<String> uniqueNames = new HashSet<>();
        Set<String> duplicateNames = namesList.stream().filter(name -> !uniqueNames.add(name)).collect(Collectors.toSet());
//        -------------------------------------------

        Map<String, Long> collectMapForNamesFrequencyCount = namesList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Set<String> names = collectMapForNamesFrequencyCount.entrySet().stream().filter(entry -> entry.getValue() > 1)
                .map(entry -> entry.getKey()).collect(Collectors.toSet());

//        -----------------------------------------

        Set<String> namesUsingFrequency = namesList.stream().filter(name -> Collections.frequency(namesList, name) > 1).collect(Collectors.toSet());


        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(namesUsingFrequency)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @GetMapping(path = "age")
    public ResponseEntity<SuccessResponse> getAllPersonsBasedOnAge(@RequestParam(name = "age") Integer personAge) {

        List<PersonBasicDTO> personBasicDTOS = personService.getAllPersonsBasedOnAge(personAge);

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(personBasicDTOS)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @GetMapping(path = "allAge")
    public ResponseEntity<SuccessResponse> getAllAge() {

        List<PersonBasicDTO> all = personService.getAllPersons();

        List<Integer> ages = all.stream().map(person -> person.getAge()).sorted((o1,o2) -> o2.compareTo(o1)).distinct().collect(Collectors.toList());

        IntSummaryStatistics statistics = ages.stream().mapToInt(x -> x).summaryStatistics();

        int max = ages.stream().mapToInt(x -> x).summaryStatistics().getMax();

        int min = ages.stream().mapToInt(x -> x).summaryStatistics().getMin();

        double average = ages.stream().mapToInt(x -> x).summaryStatistics().getAverage();

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Ages Provided")
                        .data(average)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @GetMapping(path = "name-list-inString")
    public ResponseEntity<SuccessResponse> getAllPersonNamesInString() {

        List<PersonBasicDTO> all = personService.getAllPersons();

        List<String> namesList = all.stream().map(person -> person.getPersonName()).sorted().collect(Collectors.toList());

        String string = namesList.stream().map(name -> name).collect(Collectors.joining(", "));

        Map<String, Long> count = namesList.stream().map(name -> name).collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(count)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @GetMapping(path = "key-value-pair-by-age")
    public ResponseEntity<SuccessResponse> getAllPersonsKeyValuePairBasedOnAge() {

        List<PersonBasicDTO> all = personService.getAllPersons();

        Map<Integer, List<PersonBasicDTO>> collectedMapByAgeList =
                all.stream().collect(Collectors.groupingBy(person -> person.getAge()));

        Map<Integer, Set<PersonBasicDTO>> collectMapByAgeSet =
                all.stream().collect(Collectors.groupingBy(person -> person.getAge(), Collectors.toSet()));

        Map<Integer, Set<PersonBasicDTO>> collectMapByAgeSetSorted =
                all.stream().collect(Collectors.groupingBy(person -> person.getAge(), TreeMap::new,  Collectors.toSet()));


        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Persons Data Provided")
                        .data(collectMapByAgeSetSorted)
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .build());

    }

    @PutMapping(path = "")
    public ResponseEntity<SuccessResponse> updatePerson(
            @RequestParam(name = "pid") Integer personId,
            @RequestBody PersonRegistrationDTO personRegistrationDTO) {

        boolean personUpdate = personService.updatePerson(personId, personRegistrationDTO);

        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Person Data Updated")
                        .status(HttpStatus.OK)
                        .data(personUpdate)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping(path = "")
    public  ResponseEntity<SuccessResponse> deletePerson(@RequestParam(name = "pid") Integer personId) {
        Integer personDelete = personService.deletePerson(personId);
        return ResponseEntity.<SuccessResponse>ofNullable(
                SuccessResponse.builder()
                        .message("Person Deleted")
                        .status(HttpStatus.OK)
                        .data(personId)
                        .timestamp(LocalDateTime.now())
                        .build());
    }


}
