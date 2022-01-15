package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveRecurso {
    Mono<Recurso> apply(Recurso recurso);
}
