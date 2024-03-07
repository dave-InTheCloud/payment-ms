package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.*;
import lu.dave.finance.payment.service.AccountService;
import lu.dave.finance.payment.service.MovementService;
import lu.dave.finance.payment.util.PageValidationUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static lu.dave.finance.payment.config.ServletConfig.BAS_PATH_API;

@AllArgsConstructor
@RestController
@RequestMapping(BAS_PATH_API +"/accounts")
public class AccountController {

    private final AccountService accountServiceImpl;

    private final MovementService movementServiceImpl;


    @GetMapping("")
    @Operation(summary = "Get all Accounts")
    public AccountDtoPageable getAll(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        PageValidationUtil.pageIsNegative(pageable);
        return accountServiceImpl.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @Operation(summary = "Create an Account")
    public AccountDto create(@Valid @RequestBody AccountDtoRequest accountDtoRequest) {
        return accountServiceImpl.save(accountDtoRequest);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get account by id")
    public AccountDtoWithCustomer getById(@PathVariable Long id) {
        return accountServiceImpl.getById(id);
    }

    @GetMapping("/{id}/movements")
    @Operation(summary = "Get movements of an account by id")
    public MovementDtoPageable getMovementsByAccountId(@PageableDefault(value = 20, page = 0, sort = "id") Pageable pageable,
                                                       @PathVariable Long id) {
        PageValidationUtil.pageIsNegative(pageable);
        return movementServiceImpl.getMovementsByAccountId(pageable, id);
    }

}
