package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.dto.CustomerDtoPageable;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAll();
    List<? extends CustomerDto> getAll();

    CustomerDtoPageable getAll(Pageable pageable);
    CustomerDtoRequest getById(Long id);

    CustomerEntity findById(Long id);


    CustomerDtoRequest save(CustomerDtoRequest customerDtoRequest);
}
