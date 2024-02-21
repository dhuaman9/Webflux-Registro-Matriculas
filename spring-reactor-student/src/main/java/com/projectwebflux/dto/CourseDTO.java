package com.projectwebflux.dto;


import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {
	
	
    private String id;

    @NotNull
    @Size(min = 4 , max = 40)
    @Indexed(unique = true)
    private String nameCourse;

    @NotNull
    @Size(min = 3)
    @Indexed(unique = true)
    private String acronymCourse;
    
    @NotNull
    private Boolean stateCourse;

}
