package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dao.MovementRepository;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.enumaration.MovementType;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.exception.NotFoundException;
import lu.dave.finance.payment.service.MovementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private ExchangeService exchangeService;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private MovementServiceImpl movementService;


    @Test
    public void testSave_fromAccountDoesNotExist() {
        // Given
        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(100.0);

        assertThrows(NotFoundException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(0)).findById(dto.getFromAccountId());
        Mockito.verify(accountServiceImpl, Mockito.times(0)).findBySerialNumber(dto.getFromSerialNumber());
    }

    @Test
    public void testSave_toAccountDoesNotExist() {
        // Given
        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToSerialNumber("serial");
        dto.setAmount(100.0);

        // When
        // Then
        assertThrows(NotFoundException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(0)).findById(dto.getToAccountId());
        Mockito.verify(accountServiceImpl, Mockito.times(1)).findBySerialNumber(dto.getToSerialNumber());
    }

    @Test
    public void testSave_exchangeRateFails() {
        // Given
        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(100.0);

        Mockito.when(exchangeService.convertAmount(anyString(), anyString(), anyDouble())).thenThrow(new RuntimeException("Exchange rate not found"));

        // When
        // Then
        assertThrows(RuntimeException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(1)).findById(dto.getFromAccountId());
    }

    @Test
    public void testSave_insufficientFunds() {
        // Given
        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setBalance(50.0);
        fromAccount.setCurrencyCode("USD");
        Mockito.when(accountServiceImpl.findById(1L)).thenReturn(fromAccount);

        AccountEntity toAccount = new AccountEntity();
        toAccount.setBalance(50.0);
        toAccount.setCurrencyCode("EUR");
        Mockito.when(accountServiceImpl.findById(2L)).thenReturn(toAccount);

        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(100.0);

        // When
        // Then
        assertThrows(BadParameterException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(1)).findById(dto.getFromAccountId());
    }
}
