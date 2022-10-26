package com.example.taco.web

import com.example.taco.Ingredient
import com.example.taco.Taco
import com.example.taco.TacoOrder
import com.example.taco.data.IngredientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid


@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
class DesignTacoController @Autowired constructor(
    private val ingredientRepo: IngredientRepository
) {
    @ModelAttribute
    fun addIngredientsToModel(model: Model) {
        val ingredients: MutableList<Ingredient> = ArrayList()
        ingredientRepo.findAll().forEach(Consumer { i: Ingredient -> ingredients.add(i) })
        val types: Array<Ingredient.Type> = Ingredient.Type.values()
        for (type in types) {
            model.addAttribute(
                type.toString().toLowerCase(),
                filterByType(ingredients, type)
            )
        }
    }

    @ModelAttribute(name = "tacoOrder")
    fun order(): TacoOrder {
        return TacoOrder()
    }

    @ModelAttribute(name = "taco")
    fun taco(): Taco {
        return Taco()
    }

    @GetMapping
    fun showDesignForm(): String {
        return "design"
    }

    @PostMapping
    fun processTaco(
        taco: @Valid Taco?, errors: Errors,
        @ModelAttribute tacoOrder: TacoOrder
    ): String {
        if (errors.hasErrors()) {
            return "design"
        }
        tacoOrder.addTaco(taco!!)
        return "redirect:/orders/current"
    }

    private fun filterByType(
        ingredients: List<Ingredient>, type: Ingredient.Type
    ): Iterable<Ingredient> {
        return ingredients
            .stream()
            .filter { (_, _, type1): Ingredient -> type1 == type }
            .collect(Collectors.toList())
    }
}