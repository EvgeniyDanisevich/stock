package ru.evgeniy.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.evgeniy.stock.service.StockService;

@SpringBootApplication
@EnableScheduling
public class StockApplication {

	private static final Logger log = LoggerFactory.getLogger(StockApplication.class);

	private final StockService service;

	public StockApplication(StockService service) {
		this.service = service;
	}

	@Scheduled(cron = "0 0/10 * * * ?")
	public void saveAndUpdatePrices() {
		service.saveAllPrices();
		service.updateCompanies();
		log.info("Data updated");
	}

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}
}
