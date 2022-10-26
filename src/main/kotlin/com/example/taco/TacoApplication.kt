package com.example.taco

import com.example.taco.data.IngredientRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import com.example.taco.Ingredient
import com.example.taco.TacoCloudApplication


@SpringBootApplication
class TacoCloudApplication{

	@Bean
	fun dataLoader(repo: com.example.taco.data.IngredientRepository): CommandLineRunner {
		return CommandLineRunner {
			repo.deleteAll() // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
			repo.save(Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP))
			repo.save(Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP))
			repo.save(Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN))
			repo.save(Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN))
			repo.save(Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES))
			repo.save(Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES))
			repo.save(Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE))
			repo.save(Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE))
			repo.save(Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE))
			repo.save(Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE))
		}
	}
}

fun main(args: Array<String>) {
	runApplication<TacoCloudApplication>(*args)
	//SpringApplication.run(TacoCloudApplication::class.java, *args)
}
