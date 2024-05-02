package com.jsp.pms.response;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private String error;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
