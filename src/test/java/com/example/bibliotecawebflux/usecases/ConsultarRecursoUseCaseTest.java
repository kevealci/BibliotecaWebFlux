package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;


class ConsultarRecursoUseCaseTest {

    IRecursoRepository recursoRepository;
    ConsultarRecursoUseCase consultarRecursoUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        consultarRecursoUseCase = new ConsultarRecursoUseCase(recursoRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "xxxx,true,2020-09-15,aaaa,bbbb,El recurso esta disponible",
            "xxxx,false,2020-09-15,aaaa,bbbb,No esta disponible fue prestado el 2020-09-15"
    })
    void apply(String id,
               Boolean available,
               String fechaPrestamo,
               String tipo,
               String tematica,
               String mensaje
               ) {

        var recurso = new Recurso();
        recurso.setId(id);
        recurso.setIsAvailable(available);
        recurso.setFechaPrestamo(LocalDate.parse(fechaPrestamo));
        recurso.setTipo(tipo);
        recurso.setTematica(tematica);

        Mockito.when(recursoRepository.findById(id)).thenReturn(Mono.just(recurso));

        StepVerifier.create(consultarRecursoUseCase.apply(id))
                .expectNext(mensaje)
                .verifyComplete();

        Mockito.verify(recursoRepository).findById(id);


    }
}