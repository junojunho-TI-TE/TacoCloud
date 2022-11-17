package com.example.taco.api

import com.example.taco.Taco
import com.example.taco.data.TacoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*


@RestController
@RequestMapping(path = ["/api/tacos"], produces = ["application/json"])
@CrossOrigin(origins = ["http://tacocloud:8080"])
class TacoController(tacoRepo: TacoRepository) {
    private val tacoRepo: TacoRepository

    init {
        this.tacoRepo = tacoRepo
    }

//    @GetMapping(params = ["recent"])
//    fun recentTacos(): Flux<Taco> {
//        return tacoRepo.findAll().toFlux().take(12)
//    }
//
//    @GetMapping("/{id}")
//    fun tacoById(@PathVariable("id") id: Long): Mono<Taco> {
//        return tacoRepo.findById(id)
//    }
//
//    @PostMapping(consumes = ["application/json"])
//    @ResponseStatus(HttpStatus.CREATED)
//    fun postTaco(@RequestBody tacoMono: Mono<Taco>) : Mono<Taco> {
//        return tacoRepo.saveAll(tacoMono).toMono()
//    }

//    @PostMapping(consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<Taco> postTaco(@RequestBody Mono<Taco> tacoMono) {
//        return tacoMono.flatMap(tacoRepo::save);
//    }

    @Configuration
    class RouterFunctionConfig {
        @Autowired
        private val tacoRepo: TacoRepository? = null
        @Bean
        fun routerFunction(): RouterFunction<*> {
            return route(GET("/api/tacos").and(queryParam("recent") { t -> t != null })) { request: ServerRequest? ->
                recents(
                    request
                )
            }.andRoute(POST("/api/tacos")) { request: ServerRequest -> postTaco(request) }
        }

        fun recents(request: ServerRequest?): Mono<ServerResponse?> {
            return ServerResponse.ok().body(tacoRepo!!.findAll().take(12), Taco::class.java)
        }

        fun postTaco(request: ServerRequest): Mono<ServerResponse?> {
            return request.bodyToMono(Taco::class.java).flatMap { taco -> tacoRepo!!.save(taco) }.flatMap { savedTaco ->
                ServerResponse.created(URI.create("http:/ /localhost:8080/api/tacos/" + savedTaco.id)).body(
                    savedTaco,
                    Taco::class.java
                )
            }
        }
    }
}