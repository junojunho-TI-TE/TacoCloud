package com.example.taco.api

import com.example.taco.Taco
import com.example.taco.TacoOrder
import com.example.taco.data.TacoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping(path = ["/api/tacos"], produces = ["application/json"])
@CrossOrigin(origins = ["http://tacocloud:8080"])
class TacoController(tacoRepo: TacoRepository) {
    private val tacoRepo: TacoRepository

    init {
        this.tacoRepo = tacoRepo
    }

    @GetMapping(params = ["recent"])
    fun recentTacos(): Iterable<Taco> {
        val page = PageRequest.of(0, 12, Sort.by("createdAt").descending())
        return tacoRepo.findAll(page)
    }

    @GetMapping("/{id}")
    fun tacoById(@PathVariable("id") id: Long): ResponseEntity<Taco> {
        val optTaco: Optional<Taco> = tacoRepo.findById(id)
        return if (optTaco.isPresent) {
            ResponseEntity<Taco>(optTaco.get(), HttpStatus.OK)
        } else ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun postTaco(@RequestBody taco: Taco): Taco {
        return tacoRepo.save(taco)
    }
}