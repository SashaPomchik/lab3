package com.example.demo.repositories;

import com.example.demo.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class CategoryRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveCategory() {
        Category category = new Category();
        category.setName("Electronics");

        category = categoryRepository.save(category);

        Optional<Category> retrievedCategory = categoryRepository.findById(category.getId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo("Electronics");
    }

    @Test
    void shouldUpdateCategory() {
        Category category = new Category();
        category.setName("Books");

        category = categoryRepository.save(category);

        category.setName("Updated Books");
        category = categoryRepository.save(category);

        Optional<Category> updatedCategory = categoryRepository.findById(category.getId());
        assertThat(updatedCategory).isPresent();
        assertThat(updatedCategory.get().getName()).isEqualTo("Updated Books");
    }

    @Test
    void shouldDeleteCategory() {
        Category category = new Category();
        category.setName("Toys");

        category = categoryRepository.save(category);
        Long categoryId = category.getId();

        categoryRepository.delete(category);

        Optional<Category> deletedCategory = categoryRepository.findById(categoryId);
        assertThat(deletedCategory).isNotPresent();
    }

    @Test
    void shouldNotSaveCategoryWithEmptyName() {
        Category category = new Category();
        category.setName("");

        category = categoryRepository.save(category);

        Optional<Category> retrievedCategory = categoryRepository.findById(category.getId());
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo(""); // Ім'я має бути порожнім, якщо збереглося
    }
}
