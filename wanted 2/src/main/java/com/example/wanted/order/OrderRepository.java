package com.example.wanted.order;

import com.example.wanted.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(long orderId);
    List<Order> findByBuyerMemberId(long memberId);

    @Query("SELECT o FROM Order o WHERE (o.buyer.memberId = :memberId OR o.product.seller.memberId = :memberId) AND o.product.productStatus = :productStatus")
    List<Order> findReservedOrdersByMemberId(long memberId);
}
