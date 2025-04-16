package com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.repository;

import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.DistributionCentreItem;
import com.CPAN_228_Group10.CPAN_228_FinalProject.humber.warehouse.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DistributionCentreItemRepository extends JpaRepository<DistributionCentreItem, Long> {
    List<DistributionCentreItem> findByItemBrandAndItemName(Brand brand, String name);
    List<DistributionCentreItem> findByDistributionCentreId(Long distributionCentreId);
}
