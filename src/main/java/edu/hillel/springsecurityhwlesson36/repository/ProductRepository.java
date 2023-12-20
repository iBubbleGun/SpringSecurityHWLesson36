package edu.hillel.springsecurityhwlesson36.repository;

import edu.hillel.springsecurityhwlesson36.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
