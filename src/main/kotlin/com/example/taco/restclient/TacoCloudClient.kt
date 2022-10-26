package com.example.taco.restclient

import com.example.taco.Ingredient
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI


@Service
@Slf4j
class TacoCloudClient(private val rest: RestTemplate) {

//    fun getIngredientById(ingredientId: String?): Ingredient? {
//        return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient::class.java, ingredientId)
//    }

//    fun getIngredientById(ingredientId: String): Ingredient? {
//        val urlVariables: MutableMap<String, String> = HashMap()
//        urlVariables["id"] = ingredientId
//        return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient::class.java, urlVariables)
//    }

//    fun getIngredientById(ingredientId: String): Ingredient? {
//        val urlVariables: MutableMap<String, String> = HashMap()
//        urlVariables["id"] = ingredientId
//        val url: URI = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}").build(urlVariables)
//        return rest.getForObject(url, Ingredient::class.java)
//    }

    fun getIngredientById(ingredientId: String?): Ingredient? {
        val responseEntity = rest.getForEntity(
            "http://localhost:8080/ingredients/{id}",
            Ingredient::class.java, ingredientId
        )
        //log.info("Fetched time: {}", responseEntity.headers.date)
        return responseEntity.body
    }

    fun updateIngredient(ingredient: Ingredient) {
        rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.id)
    }

    fun deleteIngredient(ingredient: Ingredient) {
        rest.delete("http://localhost:8080/ingredients/{id}", ingredient.id)
    }

//    fun createIngredient(ingredient: Ingredient?): Ingredient? {
//        return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient::class.java)
//    }

//    fun createIngredient(ingredient: Ingredient?): URI? {
//        return rest.postForLocation("http://localhost:8080/ingredients", ingredient)
//    }

    fun createIngredient(ingredient: Ingredient?): Ingredient? {
        val responseEntity = rest.postForEntity(
            "http://localhost:8080/ingredients", ingredient,
            Ingredient::class.java
        )
        //log.info("New resource created at {}", responseEntity.headers.location)
        return responseEntity.body
    }
}