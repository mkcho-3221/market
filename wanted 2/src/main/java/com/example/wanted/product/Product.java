package com.example.wanted.product;

import com.example.wanted.member.Member;
import com.example.wanted.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String productName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private  ProductStatus productStatus = ProductStatus.PRODUCT_AVAILABLE;
    private int price;

    public enum ProductStatus{
        PRODUCT_AVAILABLE(1, "판매중"),
        PRODUCT_RESERVED(2, "예약중"),
        PRODUCT_SOLD(3, "완료");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        ProductStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member seller;

    public void addMember(Member seller){
        this.seller = seller;
    }

    @OneToOne(mappedBy = "product")
    private Order order;
}
