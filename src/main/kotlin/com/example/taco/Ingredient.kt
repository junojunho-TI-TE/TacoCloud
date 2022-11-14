package com.example.taco

import lombok.*
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
data class Ingredient(@Id var id:String = "", var name:String = "",  var type:Type = Type.WRAP ) {

    enum class Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}

