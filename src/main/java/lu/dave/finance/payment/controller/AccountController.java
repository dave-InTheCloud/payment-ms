package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.*;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.service.AccountService;
import lu.dave.finance.payment.service.MovementService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountServiceImpl;

    private final MovementService movementServiceImpl;


    @GetMapping("")
    public List<? extends AccountDto> getAll() {
        return accountServiceImpl.findAll();
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

        // by default return the first page 0
        if(pageable.getPageNumber() < 0) throw new BadParameterException("Provide a pagination greater or equal to 0");

        return movementServiceImpl.getMovementsByAccountId(pageable, id);
    }
}
