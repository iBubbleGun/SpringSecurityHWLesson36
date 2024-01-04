package edu.hillel.springsecurityhwlesson36.controller;

import edu.hillel.springsecurityhwlesson36.exception.ProductIdNotFoundException;
import edu.hillel.springsecurityhwlesson36.model.Product;
import edu.hillel.springsecurityhwlesson36.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product).getBody();
    }

    @GetMapping("/get/{productId}")
    public Product getProductById(@PathVariable(name = "productId") Long productId) throws ProductIdNotFoundException {
        return productService.getProductById(productId).getBody();
    }

    @GetMapping("/get")
    public List<Product> getAllProducts() {
        return productService.getAllProducts().getBody();
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable(name = "productId") Long productId) {
        return productService.deleteProductById(productId).getBody();
    }
}
