package ru.evgeniy.stock.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.evgeniy.stock.service.StockService;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private final StockService service;

    public LoadDatabase(StockService service) {
        this.service = service;
    }

    @Bean
    public CommandLineRunner initDatabase() {

        return args -> {
            service.initRepositories();
            log.info("Repositories is loaded");
        };
    }
}