package ru.evgeniy.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class CompanyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    private String ticker;
    private String company;
    private String sector;
    private String industry;
    private BigDecimal lastPrice;

    public static CompanyModel of(String ticker, String company, String sector, String industry, BigDecimal lastPrice) {
        CompanyModel model = new CompanyModel();
        model.ticker = ticker;
        model.company = company;
        model.sector = sector;
        model.industry = industry;
        model.lastPrice = lastPrice;
        return model;
    }
}
