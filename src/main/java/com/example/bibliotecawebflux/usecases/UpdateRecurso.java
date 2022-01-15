package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateRecurso {
    Mono<Recurso> apply(Recurso recurso);
}
