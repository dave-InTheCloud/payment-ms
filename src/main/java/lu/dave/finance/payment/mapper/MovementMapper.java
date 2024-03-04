package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.*;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface MovementMapper extends Converter<MovementDtoRequest, MovementEntity> {

    MovementEntity convert(MovementDtoRequest movementDtoRequest);

    MovementDto convertToDto(MovementDtoRequest movementDto);

}
