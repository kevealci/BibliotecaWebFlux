package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class RecomendarRecursosUseCase implements BiFunction<Optional<String>,Optional<String>, Flux<Recurso>> {

    private final IRecursoRepository recursoRepository;

    public RecomendarRecursosUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public Flux<Recurso> apply(Optional<String> tipo, Optional<String> tematica) {

        if(tipo.isPresent() && tematica.isPresent()) {
            return recursoRepository.findByTipoAndTematica(tipo.get(), tematica.get());
        } else if (tipo.isPresent()) {
            return recursoRepository.findByTipo(tipo.get());
        } else if (tematica.isPresent()) {
            return recursoRepository.findByTematica(tematica.get());
        }
        return Flux.empty();
    }
}
