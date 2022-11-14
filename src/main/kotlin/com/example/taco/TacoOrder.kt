package com.example.taco

import lombok.Data
import org.hibernate.validator.constraints.CreditCardNumber
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern


@Data
@Entity
@Table(name="Taco_Order")
class TacoOrder : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    val placedAt = Date()

    @NotBlank(message = "Delivery name is required")
    var deliveryName: String? = null

    @NotBlank(message = "Street is required")
    var deliveryStreet: String? = null

    @NotBlank(message = "City is required")
    var deliveryCity: String? = null

    @NotBlank(message = "State is required")
    var deliveryState: String? = null

    @NotBlank(message = "Zip code is required")
    var deliveryZip: String? = null
    var ccNumber: @CreditCardNumber(message = "Not a valid credit card number") String? = null

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    var ccExpiration: String? = null
    var ccCVV: @Digits(integer = 3, fraction = 0, message = "Invalid CVV") String? = null

    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var tacos: MutableList<Taco> = ArrayList()

    @ManyToOne
    public var user:User = User()
    fun addTaco(taco: Taco) {
        tacos.add(taco)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}