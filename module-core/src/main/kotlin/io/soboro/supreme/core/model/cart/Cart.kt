package io.soboro.supreme.core.model.cart

import io.soboro.supreme.core.model.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Cart(
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "cart") var cartItems: MutableList<CartItem>,
) : BaseEntity() {

    fun put(cartItem: CartItem) {
        this.cartItems.add(cartItem)
    }

    fun modify(cartItem: CartItem) {
        this.cartItems.remove(cartItem)
        this.cartItems.add(cartItem)
    }
}