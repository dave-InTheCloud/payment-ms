package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerServiceImpl;

    @GetMapping("")
    public List<CustomerEntity> getAll() {
        return customerServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id) {
        return customerServiceImpl.getById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public CustomerDto create(@Valid @RequestBody CustomerDto customerDto) {
        return customerServiceImpl.save(customerDto);
    }


}
