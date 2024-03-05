package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.*;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.service.AccountService;
import lu.dave.finance.payment.service.MovementService;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lu.dave.finance.payment.config.ServletConfig.basePathApi;

@AllArgsConstructor
@RestController
@RequestMapping(basePathApi+"/accounts")
public class AccountController {

    private final AccountService accountServiceImpl;

    private final MovementService movementServiceImpl;


    @GetMapping("")
    public List<? extends AccountDto> getAll(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        return accountServiceImpl.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public AccountDto create(@Valid @RequestBody AccountDtoRequest accountDtoRequest) {
        return accountServiceImpl.save(accountDtoRequest);
    }


    @GetMapping("/{id}")
    public AccountDtoWithCustomer getById(@PathVariable Long id) {
        return accountServiceImpl.getById(id);
    }

    @GetMapping("/{id}/movements")
    public MovementDtoPageable getMovementsByAccountId(@PageableDefault(value = 20, page = 0) Pageable pageable,
                                                       @PathVariable Long id) {
        PageValidationUtil.pageIsNegative(pageable);
        return movementServiceImpl.getMovementsByAccountId(pageable, id);
    }

}
