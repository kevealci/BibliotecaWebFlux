package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class DeleteRecursoUseCase implements DeleteRecurso {

    private final IRecursoRepository recursoRepository;

    public DeleteRecursoUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id);
        return recursoRepository.deleteById(id);
    }
}
