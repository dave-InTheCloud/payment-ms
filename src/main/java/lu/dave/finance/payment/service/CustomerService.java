package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAll();

    CustomerDto getById(Long id);

    CustomerEntity findById(Long id);


    CustomerDto save(CustomerDto customerDto);
}
