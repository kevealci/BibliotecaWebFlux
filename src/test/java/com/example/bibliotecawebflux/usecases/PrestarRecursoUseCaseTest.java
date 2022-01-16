package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PrestarRecursoUseCaseTest {

    IRecursoRepository recursoRepository;
    PrestarRecursoUseCase prestarRecursoUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        prestarRecursoUseCase = new PrestarRecursoUseCase(recursoRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "xxxx,true,2020-09-15,aaaa,bbbb,1,El recurso esta disponible",
            "xxxx,false,2020-09-15,aaaa,bbbb,0,No esta disponible fue prestado el 2020-09-15"
    })
    void apply(String id,
               Boolean available,
               String fechaPrestamo,
               String tipo,
               String tematica,
               Integer check,
               String mensaje
    ) {

        var recurso = new Recurso();
        recurso.setId(id);
        recurso.setIsAvailable(available);
        recurso.setFechaPrestamo(LocalDate.parse(fechaPrestamo));
        recurso.setTipo(tipo);
        recurso.setTematica(tematica);

        Mockito.when(recursoRepository.findById(id)).thenReturn(Mono.just(recurso));
        Mockito.when(recursoRepository.save(recurso)).thenReturn(Mono.just(recurso));

        StepVerifier.create(prestarRecursoUseCase.apply(id))
                .expectNext(mensaje)
                .verifyComplete();

        if(check == 0){Mockito.verify(recursoRepository).findById(id);}
        if(check == 1){
            Mockito.verify(recursoRepository).findById(id);
            Mockito.verify(recursoRepository).save(recurso);
        }



    }
}