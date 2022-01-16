package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecomendarRecursosUseCaseTest {

    IRecursoRepository recursoRepository;
    RecomendarRecursosUseCase recomendarRecursosUseCase;

    @BeforeEach
    public void setUp() throws Exception {
        recursoRepository = Mockito.mock(IRecursoRepository.class);
        recomendarRecursosUseCase = new RecomendarRecursosUseCase(recursoRepository);
    }

    @ParameterizedTest
    @CsvSource({
            "xxxx,true,2020-09-15,aaaa,bbbb,0",
            "xxxx,false,2020-09-15,aaaa,bbbb,1",
            "xxxx,true,2020-09-15,aaaa,bbbb,2",
            "xxxx,true,2020-09-15,aaaa,bbbb,3"
    })
    void apply(String id,
               Boolean available,
               String fechaPrestamo,
               String tipo,
               String tematica,
               Integer check
    ) {

        var recurso = new Recurso();
        recurso.setId(id);
        recurso.setIsAvailable(available);
        recurso.setFechaPrestamo(LocalDate.parse(fechaPrestamo));
        recurso.setTipo(tipo);
        recurso.setTematica(tematica);

        Optional<String> tipoVacio = Optional.empty();
        Optional<String> tematicaVacio = Optional.empty();
        Optional<String> queryTipo = Optional.of("aaaa");
        Optional<String> queryTematica = Optional.of("bbbb");

        Mockito.when(recursoRepository.findByTipoAndTematica(queryTipo.get(), queryTematica.get())).thenReturn(Flux.just(recurso));
        Mockito.when(recursoRepository.findByTipo(queryTipo.get())).thenReturn(Flux.just(recurso));
        Mockito.when(recursoRepository.findByTematica(queryTematica.get())).thenReturn(Flux.just(recurso));

        if(check == 0){
            StepVerifier.create(recomendarRecursosUseCase.apply(tipoVacio,tematicaVacio))
                    .expectNext()
                    .verifyComplete();
        }
        if(check == 1){
            StepVerifier.create(recomendarRecursosUseCase.apply(queryTipo,queryTematica))
                    .expectNext(recurso)
                    .verifyComplete();
            Mockito.verify(recursoRepository).findByTipoAndTematica(queryTipo.get(), queryTematica.get());
        }
        if(check == 2){
            StepVerifier.create(recomendarRecursosUseCase.apply(queryTipo,tematicaVacio))
                    .expectNext(recurso)
                    .verifyComplete();
            Mockito.verify(recursoRepository).findByTipo(tipo);
        }
        if(check == 3){
            StepVerifier.create(recomendarRecursosUseCase.apply(tipoVacio,queryTematica))
                    .expectNext(recurso)
                    .verifyComplete();
            Mockito.verify(recursoRepository).findByTematica(tematica);
        }
    }
}