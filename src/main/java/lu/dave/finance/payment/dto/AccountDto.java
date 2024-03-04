package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"parentId"})
public class AccountDto extends AccountDtoRequest {

    private Long id;

    private CustomerDto customer;
}
