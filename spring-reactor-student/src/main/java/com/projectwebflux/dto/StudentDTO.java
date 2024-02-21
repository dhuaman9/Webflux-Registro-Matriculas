package com.projectwebflux.dto;



import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class StudentDTO {
	
	
    private String id;

    @NotNull
    @Size(min = 4 , max = 60)
    private String namesStudent;

    @NotNull
    @Size(min = 4 , max = 60)
    private String surnamesStudent;
    
    @NotNull
    @Indexed(unique = true)
    private String dniStudent;

    @NotNull
    @Min(value = 18)
    @Max(value = 80)
    private int ageStudent;

}
