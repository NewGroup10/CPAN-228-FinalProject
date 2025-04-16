package com.CPAN_228_Group10.CPAN_228_FinalProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Brand;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.DistributionCentre;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.DistributionCentreItem;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Item;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreItemRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.DistributionCentreRepository;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository.ItemRepository;

@SpringBootApplication
public class Cpan228FinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cpan228FinalProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ItemRepository itemRepository,
								DistributionCentreRepository distributionCentreRepository,
								DistributionCentreItemRepository distributionCentreItemRepository) {
		return (args) -> {
			Item tshirt = new Item("T-shirt", Brand.BALENCIAGA, 2022, 1200.00);
			Item jeans = new Item("Jeans", Brand.STONE_ISLAND, 2022, 1500.50);
			Item jacket = new Item("Jacket", Brand.DIOR, 2022, 2000.00);
			Item hoodie = new Item("Hoodie", Brand.BALENCIAGA, 2023, 1800.00);
			Item pants = new Item("Pants", Brand.STONE_ISLAND, 2024, 1300.00);
			Item sweater = new Item("Sweater", Brand.DIOR, 2022, 2200.00);

			itemRepository.save(tshirt);
			itemRepository.save(jeans);
			itemRepository.save(jacket);
			itemRepository.save(hoodie);
			itemRepository.save(pants);
			itemRepository.save(sweater);

			DistributionCentre dc1 = new DistributionCentre();
			dc1.setName("Toronto Distribution Centre");
			dc1.setLatitude(43.6532);
			dc1.setLongitude(-79.3832);
			distributionCentreRepository.save(dc1);

			DistributionCentre dc2 = new DistributionCentre();
			dc2.setName("Vancouver Distribution Centre");
			dc2.setLatitude(49.2827);
			dc2.setLongitude(-123.1207);
			distributionCentreRepository.save(dc2);

			DistributionCentre dc3 = new DistributionCentre();
			dc3.setName("Montreal Distribution Centre");
			dc3.setLatitude(45.5017);
			dc3.setLongitude(-73.5673);
			distributionCentreRepository.save(dc3);

			DistributionCentre dc4 = new DistributionCentre();
			dc4.setName("Calgary Distribution Centre");
			dc4.setLatitude(51.0447);
			dc4.setLongitude(-114.0719);
			distributionCentreRepository.save(dc4);

			DistributionCentreItem dci1 = new DistributionCentreItem();
			dci1.setDistributionCentre(dc1);
			dci1.setItem(tshirt);
			dci1.setQuantity(10);
			distributionCentreItemRepository.save(dci1);

			DistributionCentreItem dci2 = new DistributionCentreItem();
			dci2.setDistributionCentre(dc2);
			dci2.setItem(jeans);
			dci2.setQuantity(15);
			distributionCentreItemRepository.save(dci2);

			DistributionCentreItem dci3 = new DistributionCentreItem();
			dci3.setDistributionCentre(dc3);
			dci3.setItem(jacket);
			dci3.setQuantity(8);
			distributionCentreItemRepository.save(dci3);

			DistributionCentreItem dci4 = new DistributionCentreItem();
			dci4.setDistributionCentre(dc4);
			dci4.setItem(hoodie);
			dci4.setQuantity(12);
			distributionCentreItemRepository.save(dci4);
		};
	}
}
