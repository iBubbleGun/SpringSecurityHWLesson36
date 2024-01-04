package edu.hillel.springsecurityhwlesson36.service;

import edu.hillel.springsecurityhwlesson36.exception.ProductIdNotFoundException;
import edu.hillel.springsecurityhwlesson36.model.Product;
import edu.hillel.springsecurityhwlesson36.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<String> addProduct(Product product) {
        LOG.info("Call addProduct() method with product={} parameter.", product);
        productRepository.save(product);
        if (productRepository.existsById(product.getId())) {
            LOG.info("New product " + product + " successfully added.");
            return ResponseEntity.status(HttpStatus.OK).body("New product " + product + " successfully added.");
        } else {
            LOG.error("Error occurred while trying to add product: " + product);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please, try again.");
        }
    }

    public ResponseEntity<Product> getProductById(Long productId) throws ProductIdNotFoundException {
        LOG.info("Call getProductById() method with productId={} parameter.", productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                productRepository.findById(productId).orElseThrow(() -> new ProductIdNotFoundException(
                        "Product with id=" + productId + " not found!"))
        );
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        LOG.info("Call getAllProducts() method.");
        return ResponseEntity.status(HttpStatus.OK).body(
                productRepository.findAll()
        );
    }

    public ResponseEntity<String> deleteProductById(Long productId) {
        LOG.info("Call deleteProductById() method with productId={} parameter.", productId);
        productRepository.deleteById(productId);
        if (!productRepository.existsById(productId)) {
            LOG.info("Product with id=" + productId + " successfully deleted.");
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Product with id=" + productId + " successfully deleted.");
        } else {
            LOG.error("Error occurred while trying to delete product with id=" + productId + ".");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error occurred while trying to delete product with id=" + productId + ".");
        }
    }
}
