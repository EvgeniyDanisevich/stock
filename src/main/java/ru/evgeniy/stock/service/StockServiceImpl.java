package ru.evgeniy.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.evgeniy.stock.entity.CompanyModel;
import ru.evgeniy.stock.entity.PriceModel;
import ru.evgeniy.stock.parser.JsoupCompanyParser;
import ru.evgeniy.stock.parser.RegularMarketPriceParser;
import ru.evgeniy.stock.repository.CompanyRepository;
import ru.evgeniy.stock.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    private final CompanyRepository companyRepository;
    private final PriceRepository priceRepository;

    public StockServiceImpl(CompanyRepository companyRepository, PriceRepository priceRepository) {
        this.companyRepository = companyRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public void initRepositories() {
        saveCompanies();
        saveAllPrices();
    }

    @Override
    public Page<CompanyModel> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public void saveAllPrices() {
        companyRepository.findAll()
                .stream()
                .map(CompanyModel::getTicker)
                .forEach(s ->  priceRepository.save(PriceModel.of(
                        new Date(System.currentTimeMillis()),
                        new BigDecimal(new RegularMarketPriceParser().yahooParse(s)),
                        companyRepository.findByTicker(s))));
    }

    @Override
    public List<PriceModel> getAllPrices() {
        return priceRepository.findAll();
    }

    @Override
    public List<PriceModel> getAllPricesByTicker(String ticker) {
        return priceRepository.findByCompanyEquals(companyRepository.findByTicker(ticker));
    }

    @Override
    public void saveCompanies() {
        JsoupCompanyParser parser = new JsoupCompanyParser();
        RegularMarketPriceParser priceParser = new RegularMarketPriceParser();
        Map<String, Map<String, String>> parsedData = parser.fvParse();
        parsedData.values()
                .forEach(map -> companyRepository.save(CompanyModel.of(
                        map.get("ticker"),
                        map.get("company"),
                        map.get("sector"),
                        map.get("industry"),
                        new BigDecimal(priceParser.yahooParse(map.get("ticker")))
                )));
    }

    @Override
    public void updateCompanies() {
        RegularMarketPriceParser priceParser = new RegularMarketPriceParser();
        List<CompanyModel> companies = companyRepository.findAll();
        companies.forEach(company -> company.setLastPrice(new BigDecimal(priceParser.yahooParse(company.getTicker()))));
        companies.forEach(companyRepository::save);
    }
}
