package com.management.recipe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Entity;

@Entity
@DynamicUpdate
@Table(name = "ingredients")
@Data
@EqualsAndHashCode(exclude = "recipeIngredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String ingredient;

    @ManyToMany(mappedBy = "recipeIngredients", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties("recipeIngredients")
    private Set<Recipe> recipeIngredients;

    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
