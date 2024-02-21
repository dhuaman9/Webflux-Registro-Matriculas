package com.projectwebflux.security;

import java.util.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLogin {
    private String message;
    private Date timestamp;
}
