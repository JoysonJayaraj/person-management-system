package com.jsp.pms.dto;

import com.jsp.pms.entities.Person;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonBasicDTO {

    private Integer personId;

    private String personName;

    private Integer age;

    private String emailId;

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof PersonBasicDTO)) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonBasicDTO dto = (PersonBasicDTO) o;

        return this.personName.equalsIgnoreCase(dto.personName);
    }

    @Override
    public int hashCode() {
        return personName != null ? personName.hashCode() : 0;
    }


}
