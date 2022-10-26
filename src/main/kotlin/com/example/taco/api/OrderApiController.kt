package com.example.taco.api

import com.example.taco.TacoOrder
import com.example.taco.data.OrderRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping(path = ["/api/orders"], produces = ["application/json"])
@CrossOrigin(origins = ["http:/ /localhost:8080"])
class OrderApiController(private val repo: OrderRepository) {

    @PutMapping(path = ["/{orderId}"], consumes = ["application/json"])
    fun putOrder(@PathVariable("orderId") orderId: Long?, @RequestBody order: TacoOrder): TacoOrder? {
        order.id = orderId
        return repo.save(order)
    }

    @PatchMapping(path = ["/{orderId}"], consumes = ["application/json"])
    fun patchOrder(@PathVariable("orderId") orderId: Long, @RequestBody patch: TacoOrder): TacoOrder? {
        val order: TacoOrder = repo.findById(orderId).get()
        if (patch.deliveryName != null) {
            order.deliveryName = patch.deliveryName
        }
        if (patch.deliveryStreet != null) {
            order.deliveryStreet = patch.deliveryStreet
        }
        if (patch.deliveryCity != null) {
            order.deliveryCity = patch.deliveryCity
        }
        if (patch.deliveryState != null) {
            order.deliveryState = patch.deliveryState
        }
        if (patch.deliveryZip != null) {
            order.deliveryZip = patch.deliveryZip
        }
        if (patch.ccNumber != null) {
            order.ccNumber = patch.ccNumber
        }
        if (patch.ccExpiration != null) {
            order.ccExpiration = patch.ccExpiration
        }
        if (patch.ccCVV != null) {
            order.ccCVV = patch.ccCVV
        }
        return repo.save(order)
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable("orderId") orderId: Long?) {
        try {
            repo.deleteById(orderId!!)
        } catch (e: EmptyResultDataAccessException) {
        }
    }
}