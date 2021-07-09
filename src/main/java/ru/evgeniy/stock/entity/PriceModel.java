package ru.evgeniy.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class PriceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private BigDecimal price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyModel company;

    public static PriceModel of(Date time, BigDecimal price, CompanyModel company) {
        PriceModel priceModel = new PriceModel();
        priceModel.time = time;
        priceModel.price = price;
        priceModel.company = company;
        return priceModel;
    }
}
