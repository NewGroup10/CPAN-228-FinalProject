package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity  
public class Item {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @NotNull(message = "* ⚠️ Error! Name cannot be null. This is required!")
    @NotBlank(message = "* ⚠️ Error! Name cannot be empty. This is required!")
    @Size(min = 2, message = "* ⚠️ Error! Name must be at least 2 characters long. This is required!")
    private String name;

    @NotNull(message = "* ⚠️ Error! Fashionable Brand cannot be empty. This is required!")
    private Brand brand;

    @NotNull(message = "* ⚠️ Error! Year of Creation cannot be empty. This is required!")
    @Min(value = 2022, message = "* ⚠️ Error! Year of creation must be after 2021. This is required!")
    private Integer yearOfCreation;

    @NotNull(message = "* ⚠️ Error! Price cannot be empty. This is required!")
    @Min(value = 1001, message = "* ⚠️ Error! Price must be over 1000. This is required!")
    private Double price;

    public Item(String name, Brand brand, Integer yearOfCreation, Double price) {
        this.name = name;
        this.brand = brand;
        this.yearOfCreation = yearOfCreation;
        this.price = price;
    }
}
