package io.soboro.supreme.core.model.product.entity

import io.soboro.supreme.core.model.BaseEntity
import io.soboro.supreme.core.model.Money
import io.soboro.supreme.core.model.product.enums.ProductType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
class Product(
    @Column var name: String,
    @Column var brandName: String,
    @Column var description: String,
    @Embedded var price: Money,
    @OneToOne var timeLimit: TimeLimit,
    @Enumerated(EnumType.STRING) var type: ProductType,
    @ManyToOne(fetch = FetchType.LAZY) private var productOption: ProductOption,
) : BaseEntity() {
    fun canBuy(now: LocalDateTime = LocalDateTime.now()): Boolean {
        // 구매 마감 시간 지났는지
        if (this.timeLimit.timeOver(now)) return false

        // 상품 옵션이 부족 한지
        if (this.productOption.enough()) return false

        return true
    }

    fun price(): Money {
        return this.price
    }
}