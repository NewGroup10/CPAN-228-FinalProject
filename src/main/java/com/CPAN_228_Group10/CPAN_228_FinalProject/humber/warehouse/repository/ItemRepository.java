package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository; 

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Item; 
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> { 
    Page<Item> findByBrand(Brand brand, Pageable pageable);
    Page<Item> findByYearOfCreation(Integer year, Pageable pageable);
    Page<Item> findByBrandAndYearOfCreation(Brand brand, Integer year, Pageable pageable);
    Optional<Item> findByNameAndBrand(String name, Brand brand);
}
