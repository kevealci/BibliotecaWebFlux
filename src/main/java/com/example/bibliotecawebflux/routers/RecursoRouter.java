package com.example.bibliotecawebflux.routers;

import com.example.bibliotecawebflux.collections.Recurso;
import com.example.bibliotecawebflux.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecursoRouter {

    @Bean
    public RouterFunction<ServerResponse> devolverRecurso(DevolverRecursoUseCase devolverRecursoUseCase) {
        return route(GET("v1/recursos/devolver/{id}"),
                request -> ServerResponse.ok()
                        .body(BodyInserters
                                .fromPublisher(devolverRecursoUseCase
                                        .apply(request.pathVariable("id")),
                                        String.class
                                )
                        )
        );
    }


    @Bean
    public RouterFunction<ServerResponse> recomendarRecursos(RecomendarRecursosUseCase recomendarRecursosUseCase) {
        return route(GET("v1/recursos/recomendar"),
                    request -> ServerResponse.ok()
                            .body(BodyInserters
                                    .fromPublisher(recomendarRecursosUseCase
                                            .apply(request.queryParam("tipo"),
                                                    request.queryParam("tematica")
                                            ),Recurso.class
                                    )
                            )
                );
    }

    @Bean
    public RouterFunction<ServerResponse> consultarRecurso(ConsultarRecursoUseCase consultarRecursoUseCase) {
        return route(GET("v1/recursos/consultar/{id}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters
                                .fromPublisher(consultarRecursoUseCase
                                        .apply(request.pathVariable("id")), String.class))
                );
    }

    @Bean
    public RouterFunction<ServerResponse> prestarRecurso(PrestarRecursoUseCase prestarRecursoUseCase) {
        return route(GET("v1/recursos/prestar/{id}"),
                request -> ServerResponse
                        .ok()
                        .body(BodyInserters
                                .fromPublisher(prestarRecursoUseCase
                                        .apply(request.pathVariable("id")), String.class))
        );
    }


    @Bean
    public RouterFunction<ServerResponse> getAllRecursos(GetAllRecursosUseCase getAllRecursosUseCase) {
        return route(GET("v1/recursos/getAll"),
                    request -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromPublisher(getAllRecursosUseCase.get(), Recurso.class))
                );
    }


    @Bean
    public RouterFunction<ServerResponse> createRecurso(CreateRecursoUseCase createRecursoUseCase) {
        return route(POST("v1/recursos/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Recurso.class)
                        .flatMap(createRecursoUseCase::apply)
                        .flatMap(result -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                );
    }

    @Bean
    public RouterFunction<ServerResponse> deleteRecursoById(DeleteRecursoUseCase deleteRecursoUseCase) {
        return route(DELETE("v1/recursos/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.status(HttpStatus.NO_CONTENT)
                        .body(BodyInserters.fromPublisher(deleteRecursoUseCase.apply(request.pathVariable("id")),Void.class))
                );
    }

    @Bean
    public RouterFunction<ServerResponse> updateRecurso(UpdateRecursoUseCase updateRecursoUseCase) {
        return route(PUT("v1/recursos/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Recurso.class)
                        .flatMap(recurso -> updateRecursoUseCase.apply(recurso))
                        .flatMap(result -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))

                );
    }
}
