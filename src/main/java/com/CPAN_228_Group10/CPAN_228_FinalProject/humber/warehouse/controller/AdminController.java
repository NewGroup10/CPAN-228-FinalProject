package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.controller;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.*;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/create-admin")
    public String showCreateAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create-admin";
    }

    @PostMapping("/create-admin")
    public String createAdmin(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        boolean success = userService.registerAdmin(user);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Admin user created successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to create admin user");
        }
        return "redirect:/admin/create-admin";
    }
}
