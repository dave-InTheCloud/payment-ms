package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.AccountMapper;
import lu.dave.finance.payment.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static lu.dave.finance.payment.exception.NotFoundException.NO_ENTITY;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private ConversionService conversionService;

    private CustomerMapper customerMapper;

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("customer", id));
    }

    public CustomerDto getById(Long id) {
        return customerMapper.convert(this.findById(id));
    }

    public CustomerDto save(CustomerDto customerDto) {
        existsCustomerEntityByEmail(customerDto.getEmail());
        CustomerEntity customer = conversionService.convert(customerDto, CustomerEntity.class);
        return customerMapper.convert(customerRepository.save(customer));
    }

    private boolean existsCustomerEntityByEmail(final String email) {
        boolean customerExist = customerRepository.existsCustomerEntityByEmail(email);
        if (customerExist) throw new BadParameterException(
                String.format("Customer with email %s already exist", email));
        return customerExist;
    }
}
