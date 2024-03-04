package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerServiceImpl;

    @GetMapping("")
    public List<? extends  CustomerDto> getAll() {
        return customerServiceImpl.getAll();
    }

    @GetMapping("/{id}")
    public CustomerDtoRequest getById(@PathVariable Long id) {
        return customerServiceImpl.getById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public CustomerDtoRequest create(@Valid @RequestBody CustomerDtoRequest customerDtoRequest) {
        return customerServiceImpl.save(customerDtoRequest);
    }


}
