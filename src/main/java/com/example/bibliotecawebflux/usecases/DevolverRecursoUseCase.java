package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class DevolverRecursoUseCase implements Function<String, Mono<String>> {

    private final IRecursoRepository recursoRepository;

    public DevolverRecursoUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }


    @Override
    public Mono<String> apply(String id) {
        return recursoRepository.findById(id)
                .flatMap(recurso -> {
                    if(!recurso.getIsAvailable()) {
                        recurso.setIsAvailable(true);
                        return recursoRepository.save(recurso)
                                .then(Mono.just("El recurso con id "+recurso.getId()+" fue devuelto"));
                    }
                    return Mono.just("El recurso esta disponible, no se puede devolver");
                });
    }
}
