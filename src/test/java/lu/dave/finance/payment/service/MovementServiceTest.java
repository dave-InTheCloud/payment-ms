package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.exception.BadParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class MovementServiceTest {

    @Mock
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private MovementServiceImpl movementService;


    @Test
    void testSave_exchangeRateFails() {
        // Given
        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(100.0);

        Mockito.when(exchangeService.convertAmount(anyString(), anyString(), anyDouble())).thenThrow(new RuntimeException("Exchange rate not found"));

        assertThrows(RuntimeException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(1)).findById(dto.getFromAccountId());
    }

    @Test
    void testSave_insufficientFunds() {
        // Given
        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setId(1L);
        fromAccount.setBalance(50.0);
        fromAccount.setCurrencyCode("USD");
        Mockito.when(accountServiceImpl.findById(1L)).thenReturn(fromAccount);

        AccountEntity toAccount = new AccountEntity();
        toAccount.setId(2L);
        toAccount.setBalance(50.0);
        toAccount.setCurrencyCode("USD");
        Mockito.when(accountServiceImpl.findById(2L)).thenReturn(toAccount);

        MovementDtoRequest dto = new MovementDtoRequest();
        dto.setFromAccountId(1L);
        dto.setToAccountId(2L);
        dto.setAmount(100.0);

        Mockito.when(exchangeService.convertAmount(anyString(), anyString(), anyDouble()))
                .thenReturn(100.00); // Replace with your desired return value
        // When
        // Then
        assertThrows(BadParameterException.class, () -> movementService.save(dto));
        Mockito.verify(accountServiceImpl, Mockito.times(1)).findById(dto.getFromAccountId());
    }
}
