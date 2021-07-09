package ru.evgeniy.stock.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.evgeniy.stock.entity.CompanyModel;
import ru.evgeniy.stock.entity.PriceModel;
import ru.evgeniy.stock.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

    private final StockService stockService;

    CompanyRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyModel>> getAllCompany(Pageable pageable){
        stockService.updateCompanies();
        Page<CompanyModel> companyModels = this.stockService.getAll(pageable);
        if (companyModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(companyModels.toList(), HttpStatus.OK);
    }

    @RequestMapping(value = "{ticker}/prices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PriceModel>> getAllPricesByModel(@PathVariable String ticker) {
        List<PriceModel> priceModels = this.stockService.getAllPricesByTicker(ticker);
        if (priceModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(priceModels, HttpStatus.OK);
    }

    @RequestMapping(value = "/prices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PriceModel>> getAllPrices() {
        List<PriceModel> priceModels = this.stockService.getAllPrices();
        if (priceModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(priceModels, HttpStatus.OK);
    }
}
