package lu.dave.finance.payment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.exception.BadParameterException;
import lu.dave.finance.payment.service.MovementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lu.dave.finance.payment.config.ServletConfig.basePathApi;

@AllArgsConstructor
@RestController
@RequestMapping(basePathApi + "/movements")
public class MovementController {

    private final MovementService movementServiceImpl;

    @GetMapping("")
    public List<AccountDto> getAll() {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public MovementDtoRequest create(@Valid @RequestBody MovementDtoRequest movementDto) {
        if (movementDto.getFromAccountId() == null && StringUtils.isEmpty(movementDto.getFromSerialNumber()))
            throw new BadParameterException("At least fromAccountId or fromSerialNumber should be present");

        if (movementDto.getToAccountId() == null && StringUtils.isEmpty(movementDto.getToSerialNumber()))
            throw new BadParameterException("At least toAccountId or toSerialNumber should be present");

        return movementServiceImpl.save(movementDto);
    }

}
