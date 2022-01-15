package com.example.bibliotecawebflux.repositories;

import com.example.bibliotecawebflux.collections.Recurso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface IRecursoRepository extends ReactiveMongoRepository<Recurso, String> {

    Flux<Recurso> findByTipoAndTematica(String tipo, String tematica);
    Flux<Recurso> findByTipo(String tipo);
    Flux<Recurso> findByTematica(String tematica);

}
