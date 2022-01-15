package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateRecursoUseCase implements SaveRecurso{

    private final IRecursoRepository recursoRepository;

    public CreateRecursoUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public Mono<Recurso> apply(Recurso recurso) {
        return recursoRepository.save(recurso);
    }
}
