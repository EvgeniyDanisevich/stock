package ru.evgeniy.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.evgeniy.stock.entity.CompanyModel;
import ru.evgeniy.stock.entity.PriceModel;

import java.util.List;

public interface StockService {

    void initRepositories();

    Page<CompanyModel> getAll(Pageable pageable);

    void updateCompanies();

    void saveCompanies();

    List<PriceModel> getAllPricesByTicker(String ticker);

    List<PriceModel> getAllPrices();

    void saveAllPrices();
}
