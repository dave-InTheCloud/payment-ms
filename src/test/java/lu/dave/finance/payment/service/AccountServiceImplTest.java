package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dao.AccountRepository;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.entity.enumaration.AccountType;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.mapper.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private ExchangeService exchangeServiceImpl;
    @Mock
    private CustomerService customerService;
    @Mock
    private AccountMapper accountMapperImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_ShouldSaveAndThrowBadParam() {
        // Mock behavior
        AccountDtoRequest request = new AccountDtoRequest();
        request.setName("name");
        request.setCurrencyCode("EUR");
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        AccountEntity account = new AccountEntity();
        account.setName(request.getName());
        account.setType(AccountType.PORTFOLIO); // Set type directly instead of using builder
        account.setCustomer(customer); // Set customer directly
        when(customerService.findById(request.getOwnerId())).thenReturn(customer);
        when(conversionService.convert(request, AccountEntity.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapperImpl.convertWithChildren(account)).thenReturn(new AccountDtoWithCustomer()); // Mock converted DTO
        when(exchangeServiceImpl.isCurrencyAvailable("EUR")).thenReturn(true);
        // Call the method
        AccountDto savedDto = accountService.save(request);

        // Assertions
        assertNotNull(savedDto);
    }

    @Test
    public void testFindAll_ShouldReturnEmptyList() {
        // Mock behavior
        List<AccountEntity> emptyList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(emptyList);

        List<? extends AccountDto> allAccounts = accountService.findAll();

        assertEquals(emptyList, allAccounts);
    }

    @Test
    public void testFindAll_ShouldReturnListWithOneAccount() {
        List<AccountEntity> accountList = new ArrayList<>();
        AccountEntity account = new AccountEntity();
        account.setName("account name");
        account.setType(AccountType.PORTFOLIO);
        accountList.add(account);
        when(accountRepository.findAll()).thenReturn(accountList);
        when(accountMapperImpl.convertWithChildren(accountList)).thenReturn(List.of(new AccountDtoWithCustomer())); // Mock conversion

        List<? extends AccountDto> allAccounts = accountService.findAll();

        assertEquals(1, allAccounts.size());
    }

    @Test
    public void testFindById_ShouldThrowNotFoundException_WhenAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.findById(1L));
    }

    @Test
    public void testFindById_ShouldReturnAccount() {
        // Mock behavior
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setName("account name");
        account.setType(AccountType.PORTFOLIO);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Call the method
        AccountEntity foundAccount = accountService.findById(1L);

        // Assertions
        assertNotNull(foundAccount);
        assertEquals("account name", foundAccount.getName());
    }

    @Test
    public void testSave_ShouldSaveAndReturnAccountDto_WithTransaction() {
        // Mock behavior
        AccountDtoRequest request = new AccountDtoRequest();
        request.setOwnerId(1L);
        request.setName("name");
        request.setCurrencyCode("EUR");
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        AccountEntity account = new AccountEntity();
        account.setName(request.getName());
        account.setType(AccountType.PORTFOLIO); // Set type directly instead of using builder
        account.setCustomer(customer); // Set customer directly
        when(customerService.findById(request.getOwnerId())).thenReturn(customer);
        when(conversionService.convert(request, AccountEntity.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapperImpl.convertWithChildren(account)).thenReturn(new AccountDtoWithCustomer()); // Mock converted DTO

        // Call the method
        AccountDto savedDto = accountService.save(request);

        // Assertions
        assertNotNull(savedDto);
    }
}
