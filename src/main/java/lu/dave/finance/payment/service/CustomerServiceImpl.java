package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private ConversionService conversionService;

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public CustomerEntity save(CustomerDto customerDto) {
       CustomerEntity customer =  conversionService.convert(customerDto, CustomerEntity.class);
       return  customerRepository.save(customer);
    }
}
