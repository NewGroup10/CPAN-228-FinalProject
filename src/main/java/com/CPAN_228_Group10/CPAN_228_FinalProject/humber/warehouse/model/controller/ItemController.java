package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.controller;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Item;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Brand;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.ItemRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreItemRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;

    @GetMapping("/add-item")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("brands", Brand.values());
        return "add-item";
    }

    @PostMapping("/add-item")
    public String addItem(@Valid @ModelAttribute("item") Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", Brand.values());
            return "add-item";
        }
        itemRepository.save(item);
        return "redirect:/list-items";
    }

    @GetMapping("/list-items")
    public String showItemList(
            @RequestParam(value = "brand", required = false) Brand brand,
            @RequestParam(value = "year", required = false) Integer year,
            @PageableDefault(sort = "brand", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<Item> itemsPage;
        
        if (brand != null && year != null) {
            itemsPage = itemRepository.findByBrandAndYearOfCreation(brand, year, pageable);
        } else if (brand != null) {
            itemsPage = itemRepository.findByBrand(brand, pageable);
        } else if (year != null) {
            itemsPage = itemRepository.findByYearOfCreation(year, pageable);
        } else {
            itemsPage = itemRepository.findAll(pageable);
        }

        model.addAttribute("items", itemsPage.getContent());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("year", year);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("page", itemsPage);

        return "list-items";
    }
    
    @GetMapping("/distribution-centres")
    public String showDistributionCentres(Model model) {
        model.addAttribute("distributionCentres", distributionCentreRepository.findAll());
        model.addAttribute("brands", Brand.values());
        return "distribution-centres";
    }

    @PostMapping("/request-item")
    public String requestItem(@RequestParam Brand brand, 
                            @RequestParam String name,
                            RedirectAttributes redirectAttributes) {
        try {
            var items = distributionCentreItemRepository.findByItemBrandAndItemName(brand, name);
            
            if (items.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No items found in any distribution centre for " + brand + " " + name);
                return "redirect:/distribution-centres";
            }

            var distributionCentreItem = items.get(0);
            if (distributionCentreItem.getQuantity() <= 0) {
                redirectAttributes.addFlashAttribute("error", "Item " + brand + " " + name + " is out of stock in all distribution centres");
                return "redirect:/distribution-centres";
            }

            distributionCentreItem.setQuantity(distributionCentreItem.getQuantity() - 1);
            distributionCentreItemRepository.save(distributionCentreItem);

            Item warehouseItem = itemRepository.findByNameAndBrand(name, brand)
                    .orElse(new Item(name, brand, 2024, 1000.0));
            itemRepository.save(warehouseItem);

            redirectAttributes.addFlashAttribute("success", "Successfully requested " + brand + " " + name + " from " + 
                    distributionCentreItem.getDistributionCentre().getName());
            return "redirect:/distribution-centres";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while processing your request: " + e.getMessage());
            return "redirect:/distribution-centres";
        }
    }
}
