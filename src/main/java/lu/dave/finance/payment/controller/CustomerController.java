package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.dto.CustomerDtoPageable;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.service.CustomerService;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static lu.dave.finance.payment.config.ServletConfig.basePathApi;

@AllArgsConstructor
@RestController
@RequestMapping(basePathApi + "/customers")
public class CustomerController {

    private final CustomerService customerServiceImpl;

    @GetMapping("")
    public CustomerDtoPageable getAll(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        PageValidationUtil.pageIsNegative(pageable);
        return customerServiceImpl.getAll(pageable);
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
