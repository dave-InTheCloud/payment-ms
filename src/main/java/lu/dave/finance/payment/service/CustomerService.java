package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAll();

    CustomerEntity findById(Long id);


    CustomerEntity save(CustomerDto customerDto);
}
