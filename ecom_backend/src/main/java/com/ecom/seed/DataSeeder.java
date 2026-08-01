package com.ecom.seed;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecom.entities.Cart;
import com.ecom.entities.Category;
import com.ecom.entities.UserEntity;
import com.ecom.enums.Role;
import com.ecom.repository.CartRepository;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Seeds the four Aura-Belle categories and one admin account on startup.
 * Product seeding happens separately once real product photography is available
 * (see ProductSeeder, added once images are supplied).
 */

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner
{

    private final CategoryRepository categoryRepository;

    private static final List<String> CATEGORIES = List.of(
            "Western Wear",
            "Ethnic Wear",
            "Sportswear",
            "Casual Wear"
    );

    @Override
    public void run(String... args) {
        seedCategories();
    }

    private void seedCategories() {
        for (String name : CATEGORIES) {
            if (!categoryRepository.existsByCategoryNameIgnoreCase(name)) {
                Category category = new Category();
                category.setCategoryName(name);
                categoryRepository.save(category);
            }
        }
    }
	
}
