package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.function.Function;

@Service
public class PrestarRecursoUseCase implements Function<String, Mono<String>> {

    private final IRecursoRepository recursoRepository;


    public PrestarRecursoUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public Mono<String> apply(String id) {

        return recursoRepository.findById(id).flatMap(recurso -> {
                    if(!recurso.getIsAvailable()){
                        return Mono.just("No esta disponible fue prestado el "+ recurso.getFechaPrestamo());
                    }
                    recurso.setIsAvailable(false);
                    recurso.setFechaPrestamo(LocalDate.now());
                    return recursoRepository.save(recurso).then(Mono.just("El recurso esta disponible"));
                }
        );
    }
}
