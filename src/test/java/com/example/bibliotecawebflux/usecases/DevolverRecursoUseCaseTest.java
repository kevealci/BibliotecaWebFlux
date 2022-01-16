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

class DevolverRecursoUseCaseTest {

    IRecursoRepository recursoRepository;
    DevolverRecursoUseCase devolverRecursoUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        devolverRecursoUseCase = new DevolverRecursoUseCase(recursoRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "xxxx,true,2020-09-15,aaaa,bbbb,0,El recurso esta disponible no se puede devolver",
            "xxxx,false,2020-09-15,aaaa,bbbb,1,El recurso con id xxxx fue devuelto"
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

        StepVerifier.create(devolverRecursoUseCase.apply(id))
                .expectNext(mensaje)
                .verifyComplete();

        if(check == 0){Mockito.verify(recursoRepository).findById(id);}
        if(check == 1){
            Mockito.verify(recursoRepository).findById(id);
            Mockito.verify(recursoRepository).save(recurso);
        }

    }

}