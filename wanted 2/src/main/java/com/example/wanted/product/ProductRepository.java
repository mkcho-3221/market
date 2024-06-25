package com.example.wanted.product;

import com.example.wanted.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(long productId);
    List<Product> findBySeller(Member seller);
}
