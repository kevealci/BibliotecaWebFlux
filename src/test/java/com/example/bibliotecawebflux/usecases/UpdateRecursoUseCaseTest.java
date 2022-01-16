package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRecursoUseCaseTest {

    IRecursoRepository recursoRepository;
    UpdateRecursoUseCase updateRecursoUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        updateRecursoUseCase = new UpdateRecursoUseCase(recursoRepository);
    }

    @Test
    void apply() {
        var recurso = new Recurso();
        recurso.setId("xxxx");
        recurso.setIsAvailable(true);
        recurso.setFechaPrestamo(LocalDate.parse("2020-09-15"));
        recurso.setTipo("aaaa");
        recurso.setTematica("bbbb");

        Mockito.when(recursoRepository.save(recurso)).thenReturn(Mono.just(recurso));

        StepVerifier.create(updateRecursoUseCase.apply(recurso))
                .expectNext(recurso)
                .verifyComplete();

        Mockito.verify(recursoRepository).save(recurso);
    }
}