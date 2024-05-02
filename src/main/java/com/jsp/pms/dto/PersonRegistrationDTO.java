package com.jsp.pms.dto;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonRegistrationDTO {

    private String personName;
    private Integer age;
    private String emailId;

}
