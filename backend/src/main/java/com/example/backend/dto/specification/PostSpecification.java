package com.example.backend.dto.specification;

import com.example.backend.enity.Post;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> hasPriceOrAreaBetween(String name,Double on,Double under) {
        return (root, query, cb) -> cb.between(root.get(name),on,under);
    }

    public static Specification<Post> hasPriceOrAreaGreaterThan(String name, Double on) {
        return (root, query, cb) -> cb.greaterThan(root.get(name), on);
    }

    public static Specification<Post> hasPriceOrAreaLessThan(String name, Double under) {
        return (root, query, cb) -> cb.lessThan(root.get(name), under);
    }

//    public static Specification<Post> searchInput(String request) {
//        return (root, query, cb) -> cb.like(cb.lower(root.get("full_address")), "%" + request.toLowerCase() + "%");
//    }
}
