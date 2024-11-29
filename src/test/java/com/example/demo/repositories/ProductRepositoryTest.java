package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProductRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        Category category = new Category();
        category.setName("Books");

        category = categoryRepository.save(category);

        Product product = Product.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);

        assertThat(productRepository.findById(product.getId())).isPresent();
    }

    @Test
    void shouldUpdateProduct() {
        Category category = new Category();
        category.setName("Books");
        category = categoryRepository.save(category);

        Product product = Product.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);

        product.setName("Advanced Java Programming");
        product = productRepository.save(product);

        Optional<Product> updatedProduct = productRepository.findById(product.getId());
        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get().getName()).isEqualTo("Advanced Java Programming");
    }

    @Test
    void shouldDeleteProduct() {
        Category category = new Category();
        category.setName("Books");
        category = categoryRepository.save(category);

        Product product = Product.builder()
                .name("Java Programming")
                .description("A comprehensive guide to Java.")
                .price(45.99)
                .available(true)
                .category(category)
                .build();

        product = productRepository.save(product);
        Long productId = product.getId();

        productRepository.delete(product);

        Optional<Product> deletedProduct = productRepository.findById(productId);
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    void shouldNotSaveProductWithoutCategory() {
        Product product = new Product();
        product.setName("Java Programming");
        product.setDescription("A comprehensive guide to Java.");
        product.setPrice(45.99);
        product.setAvailable(true);

        Category category = new Category();
        category.setName("Programming");
        categoryRepository.save(category);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId());
    }

}
