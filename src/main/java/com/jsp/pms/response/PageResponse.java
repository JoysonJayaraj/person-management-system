package com.jsp.pms.response;

import com.jsp.pms.dto.PersonBasicDTO;
import com.jsp.pms.entities.Person;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageResponse {

    private List<PersonBasicDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;

}
