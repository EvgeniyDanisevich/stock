package ru.evgeniy.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.evgeniy.stock.entity.CompanyModel;
import ru.evgeniy.stock.entity.PriceModel;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceModel, Long> {

    List<PriceModel> findByCompanyEquals(CompanyModel model);

}
