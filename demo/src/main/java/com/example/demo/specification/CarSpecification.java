package com.example.demo.specification;


import com.example.demo.model.Car;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class CarSpecification {

    public static Specification<Car> hasBrand(String brand) {
        return (root, query, builder) ->
                brand == null ? null : builder.equal(root.get("brand"), brand);
    }

    public static Specification<Car> hasType(Car.CarType type) {
        return (root, query, builder) ->
                type == null ? null : builder.equal(root.get("type"), type);
    }

    public static Specification<Car> minInventory(int minInventory) {
        return (root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("inventory"), minInventory);
    }

    public static Specification<Car> maxDailyFee(BigDecimal maxFee) {
        return (root, query, builder) ->
                maxFee == null ? null : builder.lessThanOrEqualTo(root.get("dailyFee"), maxFee);
    }
}
