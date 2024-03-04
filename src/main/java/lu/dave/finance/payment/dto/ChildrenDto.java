package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class ChildrenDto   {
    private Long id;
    private CustomerDto customer;
}
