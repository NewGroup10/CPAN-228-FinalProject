package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.controller;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.DistributionCentre;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.DistributionCentreItem;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Item;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Brand;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreItemRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distribution-centres")
public class DistributionCentreController {

    @Autowired
    private DistributionCentreRepository distributionCentreRepository;

    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<DistributionCentre> getAllDistributionCentres() {
        return distributionCentreRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributionCentre> getDistributionCentreById(@PathVariable Long id) {
        return distributionCentreRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{centreId}/items")
    public ResponseEntity<?> addItemToDistributionCentre(
            @PathVariable Long centreId,
            @RequestBody Map<String, Object> request) {
        DistributionCentre centre = distributionCentreRepository.findById(centreId)
                .orElse(null);
        if (centre == null) {
            return ResponseEntity.notFound().build();
        }

        String name = (String) request.get("name");
        Brand brand = Brand.valueOf((String) request.get("brand"));
        Integer quantity = request.get("quantity") != null ?
                Integer.parseInt(request.get("quantity").toString()) : 0;

        Item item = itemRepository.findByNameAndBrand(name, brand)
                .orElse(new Item(name, brand, 2024, 1000.0));
        itemRepository.save(item);

        DistributionCentreItem dci = new DistributionCentreItem();
        dci.setDistributionCentre(centre);
        dci.setItem(item);
        dci.setQuantity(quantity);
        distributionCentreItemRepository.save(dci);

        return ResponseEntity.ok(dci);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        DistributionCentreItem item = distributionCentreItemRepository.findById(itemId)
                .orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        distributionCentreItemRepository.delete(item);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items/search")
    public ResponseEntity<?> searchItems(
            @RequestParam Brand brand,
            @RequestParam String name) {

        List<DistributionCentreItem> items = distributionCentreItemRepository.findByItemBrandAndItemName(brand, name);

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @PostMapping("/items/{itemId}/decrease")
    public ResponseEntity<?> decreaseItemQuantity(@PathVariable Long itemId) {
        DistributionCentreItem item = distributionCentreItemRepository.findById(itemId)
                .orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        if (item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            distributionCentreItemRepository.save(item);
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.badRequest().body("Item is already out of stock");
        }
    }

    @PostMapping("/items/{itemId}/increase")
    public ResponseEntity<?> increaseItemQuantity(@PathVariable Long itemId) {
        DistributionCentreItem item = distributionCentreItemRepository.findById(itemId)
                .orElse(null);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        item.setQuantity(item.getQuantity() + 1);
        distributionCentreItemRepository.save(item);
        return ResponseEntity.ok(item);
    }
}
