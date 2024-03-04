package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id"})
@JsonIgnoreProperties({"parentId"})
public class AccountDto extends AccountDtoRequest {
    private Long id;

}
