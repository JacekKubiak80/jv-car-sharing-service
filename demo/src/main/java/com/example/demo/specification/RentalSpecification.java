package com.example.demo.specification;


import com.example.demo.model.Rental;
import com.example.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RentalSpecification {

    public static Specification<Rental> byUser(User user) {
        return (root, query, builder) ->
                user == null ? null : builder.equal(root.get("user"), user);
    }

    public static Specification<Rental> isActive(Boolean active) {
        return (root, query, builder) -> {
            if (active == null) return null;
            return active
                    ? builder.isNull(root.get("actualReturnDate"))
                    : builder.isNotNull(root.get("actualReturnDate"));
        };
    }

    public static Specification<Rental> rentalDateAfter(LocalDate date) {
        return (root, query, builder) ->
                date == null ? null : builder.greaterThanOrEqualTo(root.get("rentalDate"), date);
    }

    public static Specification<Rental> rentalDateBefore(LocalDate date) {
        return (root, query, builder) ->
                date == null ? null : builder.lessThanOrEqualTo(root.get("rentalDate"), date);
    }
}
