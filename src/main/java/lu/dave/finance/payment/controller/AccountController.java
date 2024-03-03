package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountServiceImpl;

    @GetMapping("")
    public List<AccountDtoWithCustomer> getAll() {
        return accountServiceImpl.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public AccountDtoWithCustomer create(@Valid @RequestBody AccountDto accountDto) {
        return accountServiceImpl.save(accountDto);
    }

}
