package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GetAllRecursosUseCaseTest {

    IRecursoRepository recursoRepository;
    GetAllRecursosUseCase getAllRecursosUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        getAllRecursosUseCase = new GetAllRecursosUseCase(recursoRepository);
    }

    @Test
    void apply() {
        var recurso = new Recurso();
        recurso.setId("xxxx");
        recurso.setIsAvailable(true);
        recurso.setFechaPrestamo(LocalDate.parse("2020-09-15"));
        recurso.setTipo("aaaa");
        recurso.setTematica("bbbb");

        Mockito.when(recursoRepository.findAll()).thenReturn(Flux.just(recurso));

        StepVerifier.create(getAllRecursosUseCase.get())
                .expectNext(recurso)
                .verifyComplete();

        Mockito.verify(recursoRepository).findAll();
    }
}