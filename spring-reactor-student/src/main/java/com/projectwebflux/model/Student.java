package com.projectwebflux.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "students")
public class Student {

	@Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String names;

    @Field
    private String surnames;
    
    @Field
    @Indexed(unique = true)
    private String dni;

    @Field
    private int age;


	
}
