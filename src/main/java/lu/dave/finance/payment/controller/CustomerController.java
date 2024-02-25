package lu.dave.finance.payment.controller;

import lombok.AllArgsConstructor;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerServiceImpl;

    @GetMapping({ "", "/" })
    public List<CustomerEntity> getAll() {
        return customerServiceImpl.findAll();
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public CustomerEntity getById(@PathVariable Long id) {
        return customerServiceImpl.findById(id);
    }
}
