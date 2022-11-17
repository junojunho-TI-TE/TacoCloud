package com.example.taco.data

import com.example.taco.Taco
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TacoRepository: ReactiveCrudRepository<Taco, Long> {
}