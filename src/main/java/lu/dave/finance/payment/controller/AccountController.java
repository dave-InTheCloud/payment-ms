package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import lu.dave.finance.payment.service.AccountService;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountServiceImpl;

    @PostMapping({ "", "/" })
    public AccountEntity create(@Valid @RequestBody AccountDto accountDto) {
        return accountServiceImpl.save(accountDto);
    }

}
