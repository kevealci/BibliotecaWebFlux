package com.example.bibliotecawebflux.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recursos")
public class Recurso {

    @Id
    private String id;
    private Boolean isAvailable;
    private LocalDate fechaPrestamo;
    private String tipo;
    private String tematica;


}
