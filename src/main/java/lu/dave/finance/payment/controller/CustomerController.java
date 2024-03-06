package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.CustomerDtoPageable;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.service.CustomerService;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static lu.dave.finance.payment.config.ServletConfig.BAS_PATH_API;

@AllArgsConstructor
@RestController
@RequestMapping(BAS_PATH_API + "/customers")
public class CustomerController {

    private final CustomerService customerServiceImpl;

    @GetMapping("")
    @Operation(summary = "Get all customers")
    public CustomerDtoPageable getAll(@PageableDefault(value = 20, page = 0, sort = "id") Pageable pageable) {
        PageValidationUtil.pageIsNegative(pageable);
        return customerServiceImpl.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    public CustomerDtoRequest getById(@PathVariable Long id) {
        return customerServiceImpl.getById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @Operation(summary = "Create customer")
    public CustomerDtoRequest create(@Valid @RequestBody CustomerDtoRequest customerDtoRequest) {
        return customerServiceImpl.save(customerDtoRequest);
    }


}
