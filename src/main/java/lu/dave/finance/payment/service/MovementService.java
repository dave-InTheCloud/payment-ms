package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.MovementDtoCreated;
import lu.dave.finance.payment.dto.MovementDtoPageable;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import org.springframework.data.domain.Pageable;

public interface MovementService {
    MovementDtoCreated save(MovementDtoRequest dto);

    MovementDtoPageable getMovementsByAccountId(final Pageable pageable, Long id);
}
