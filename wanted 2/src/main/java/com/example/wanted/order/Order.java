package com.example.wanted.order;

import com.example.wanted.member.Member;
import com.example.wanted.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "ORDERS")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member buyer;

    public void addMember(Member buyer){
        this.buyer = buyer;
    }

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public void addProduct(Product product){
        this.product = product;
    }

    public enum OrderStatus {
        IN_PROGRESS(1, "진행중"),
        COMPLETED(2, "완료");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
