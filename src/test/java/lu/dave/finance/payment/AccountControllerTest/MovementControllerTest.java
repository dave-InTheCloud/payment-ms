package lu.dave.finance.payment.AccountControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.dave.finance.payment.controller.AccountController;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.service.AccountService;
import lu.dave.finance.payment.service.MovementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static lu.dave.finance.payment.config.ServletConfig.basePathApi;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@RunWith(SpringRunner.class) // or @ExtendWith(MockitoExtension.class)
public class MovementControllerTest {

    @InjectMocks
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @MockBean
    private MovementService movementService;
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAccountValid() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();
        invalidRequest.setCurrencyCode("USD");
        invalidRequest.setBalance(10.0);
        invalidRequest.setOwnerId(1L);

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isCreated());

    }

    @Test
    public void testCreateAccountWithEmptyReq() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }
    @Test
    public void testCreateAccountWithEmptyOwner() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();
        invalidRequest.setCurrencyCode("USD");
        invalidRequest.setBalance(10.0);

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }

    @Test
    public void testCreateAccountWithEmptyCurrency() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();
        invalidRequest.setBalance(10.0);
        invalidRequest.setOwnerId(1L);

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }


    @Test
    public void testCreateAccountWithEmptyBalance() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();
        invalidRequest.setCurrencyCode("USD");

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }


    @Test
    public void testCreateAccountNegativeBalance() throws Exception {
        AccountDtoRequest invalidRequest = new AccountDtoRequest();
        invalidRequest.setCurrencyCode("USD");
        invalidRequest.setBalance(-10.0);
        invalidRequest.setOwnerId(1L);

        mockMvc.perform(post(basePathApi + "/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }
}
