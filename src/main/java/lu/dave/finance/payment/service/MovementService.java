package lu.dave.finance.payment.service;

import lu.dave.finance.payment.dto.MovementDtoRequest;

public interface MovementService {
    MovementDtoRequest save(MovementDtoRequest dto);
}
