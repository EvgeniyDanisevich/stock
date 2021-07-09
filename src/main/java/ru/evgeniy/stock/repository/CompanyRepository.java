package ru.evgeniy.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.evgeniy.stock.entity.CompanyModel;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyModel, Long> {

    Page<CompanyModel> findAll(Pageable pageable);

    List<CompanyModel> findAll();

    CompanyModel findByTicker(String ticker);

}
