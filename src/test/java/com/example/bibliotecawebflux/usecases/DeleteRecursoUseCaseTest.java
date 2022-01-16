package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class DeleteRecursoUseCaseTest {

    IRecursoRepository recursoRepository;
    DeleteRecursoUseCase deleteRecursoUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        deleteRecursoUseCase = new DeleteRecursoUseCase(recursoRepository);
    }

    @Test
    void apply() {
        Mockito.when(recursoRepository.deleteById("aaaa")).thenReturn(Mono.empty());

        StepVerifier.create(deleteRecursoUseCase.apply("aaaa"))
                .expectNext()
                .verifyComplete();

        Mockito.verify(recursoRepository).deleteById("aaaa");
    }
}