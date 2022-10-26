package com.example.taco.data

import com.example.taco.Taco
import org.springframework.data.jpa.repository.JpaRepository

interface TacoRepository : JpaRepository<Taco, Long>