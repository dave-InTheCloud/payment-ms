package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.service.AccountService;
import lu.dave.finance.payment.service.AccountServiceImpl;
import lu.dave.finance.payment.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementServiceImpl;

    @GetMapping("")
    public List<AccountDtoWithCustomer> getAll() {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public MovementDto create(@Valid @RequestBody MovementDto movementDto) {
        return movementServiceImpl.save(movementDto);
    }

}
