package com.example.bibliotecawebflux.usecases;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.repositories.IRecursoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
public class GetAllRecursosUseCase implements Supplier<Flux<Recurso>> {

    private final IRecursoRepository recursoRepository;

    public GetAllRecursosUseCase(IRecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public Flux<Recurso> get() {
        return recursoRepository.findAll();
    }
}
