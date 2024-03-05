package lu.dave.finance.payment.service;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.dto.*;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.CustomerMapper;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private ConversionService conversionService;

    private CustomerMapper customerMapper;

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public List<? extends CustomerDto> getAll() {
        return customerMapper.convert(customerRepository.findAll());
    }

    public CustomerDtoPageable getAll(Pageable pageable) {
        final Page<CustomerEntity> customerEntityPage = customerRepository.findAll(pageable);
        PageValidationUtil.validatePageNumber(customerEntityPage, pageable);

       return new CustomerDtoPageable( customerMapper.convert(customerEntityPage.getContent()),
               new PageableDto(customerEntityPage));
    }


    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("customer", id));
    }

    public CustomerDtoRequest getById(Long id) {
        return customerMapper.convert(this.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CustomerDtoRequest save(CustomerDtoRequest customerDtoRequest) {
        if (customerDtoRequest.getDateOfBirth().isAfter(LocalDate.now()))
            throw  new BadParameterException("BirthDate cannot be in the future");

        existsCustomerEntityByEmail(customerDtoRequest.getEmail());
        CustomerEntity customer = conversionService.convert(customerDtoRequest, CustomerEntity.class);
        return customerMapper.convert(customerRepository.save(customer));
    }

    private void existsCustomerEntityByEmail(final String email) {
        boolean customerExist = customerRepository.existsCustomerEntityByEmail(email);
        if (customerExist) throw new BadParameterException(
                String.format("Customer with email %s already exist", email));
    }
}
