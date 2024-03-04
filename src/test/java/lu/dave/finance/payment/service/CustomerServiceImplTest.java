package lu.dave.finance.payment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lu.dave.finance.payment.dao.CustomerRepository;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.CustomerMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @NotNull
    private static CustomerEntity johnDoeEntity() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        return customer;
    }

    @NotNull
    private static CustomerDtoRequest johnDoeDto() {
        CustomerDtoRequest customerDtoRequest = new CustomerDtoRequest();
        customerDtoRequest.setId(1L);
        customerDtoRequest.setName("John Doe");
        customerDtoRequest.setEmail("john.doe@example.com");
        return customerDtoRequest;
    }


    @Test
    public void testFindAll_ShouldReturnEmptyList() {
        // Mock behavior
        List<CustomerEntity> emptyList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(emptyList);

        // Call the method
        List<CustomerEntity> allCustomers = customerServiceImpl.findAll();

        // Assertions
        assertEquals(emptyList, allCustomers);
    }

    @Test
    public void testFindAll_ShouldReturnListWithOneCustomer() {
        // Mock behavior
        List<CustomerEntity> customerList = new ArrayList<>();
        customerList.add(johnDoeEntity());
        when(customerRepository.findAll()).thenReturn(customerList);

        // Call the method
        List<CustomerEntity> allCustomers = customerServiceImpl.findAll();

        // Assertions
        assertEquals(1, allCustomers.size());
        assertEquals("John Doe", allCustomers.get(0).getName());
    }

    @Test
    public void testFindById_ShouldThrowNotFoundException_WhenCustomerNotFound() {
        // Mock behavior
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Expected exception
        assertThrows(NotFoundException.class, () -> customerServiceImpl.findById(1L));
    }

    @Test
    public void testFindById_ShouldReturnCustomer() {
        // Mock behavior
        CustomerEntity customer = johnDoeEntity();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Call the method
        CustomerEntity foundCustomer = customerServiceImpl.findById(1L);

        // Assertions
        assertNotNull(foundCustomer);
        assertEquals("John Doe", foundCustomer.getName());
    }

    @Test
    public void testGetById_ShouldThrowNotFoundException_WhenCustomerNotFound() {
        // Mock behavior (reuse from findById test)
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Expected exception
        assertThrows(NotFoundException.class, () -> customerServiceImpl.getById(1L));
    }

    @Test
    public void testGetById_ShouldReturnCustomerDto() {
        // Mock behavior (reuse from findById test)
        CustomerEntity customer = johnDoeEntity();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        CustomerDtoRequest customerDtoRequest = johnDoeDto();
        when(customerMapper.convert(customer)).thenReturn(customerDtoRequest);

        // Call the method
        CustomerDtoRequest foundCustomerDtoRequest = customerServiceImpl.getById(1L);

        // Assertions
        assertNotNull(foundCustomerDtoRequest);
        assertEquals("John Doe", foundCustomerDtoRequest.getName());
    }


    @Test
    public void testSave_ShouldThrowBadParameterException_WhenEmailAlreadyExists() {
        // Mock behavior
        CustomerDtoRequest customerDtoRequest = johnDoeDto();
        when(customerRepository.existsCustomerEntityByEmail(customerDtoRequest.getEmail())).thenReturn(true);
        // Expected exception
        assertThrows(BadParameterException.class, () -> customerServiceImpl.save(customerDtoRequest));
    }

    @Test
    public void testSave_ShouldSaveAndReturnCustomerDto() {
        // Mock behavior
        CustomerDtoRequest customerDtoRequest = johnDoeDto();
        CustomerEntity customer = johnDoeEntity();
        when(customerRepository.existsCustomerEntityByEmail(customerDtoRequest.getEmail())).thenReturn(false);
        when(conversionService.convert(customerDtoRequest, CustomerEntity.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerDtoRequest savedCustomerDtoRequest = johnDoeDto();
        when(customerMapper.convert(customer)).thenReturn(savedCustomerDtoRequest);

        // Call the method
        CustomerDtoRequest savedDto = customerServiceImpl.save(customerDtoRequest);

        // Assertions
        assertNotNull(savedDto);
        assertEquals("John Doe", savedDto.getName());
    }
}


        // Expected exception
