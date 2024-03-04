package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.MovementDto;
import lu.dave.finance.payment.dto.MovementDtoCreated;
import lu.dave.finance.payment.dto.MovementDtoRequest;
import lu.dave.finance.payment.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface MovementMapper extends Converter<MovementDtoRequest, MovementEntity> {

    MovementDtoCreated convert(MovementEntity movementDtoRequest);

    @Mapping(target = "fromAccountId", source = "account.id")
    @Mapping(target = "toAccountId", source = "toAccount.id")
    @Mapping(target = "fromSerialNumber", source = "account.serialNumber")
    @Mapping(target = "toSerialNumber", source = "toAccount.serialNumber")
    @Mapping(target = "currency", source = "currencyCode")
    MovementDto entityToDto(MovementEntity movementEntity);

    List<MovementDto> entityToDto(List<MovementEntity> movementEntity);

    @Mapping(target = "fromAccountId", source = "account.id")
    @Mapping(target = "toAccountId", source = "toAccount.id")
    @Mapping(target = "fromSerialNumber", source = "account.serialNumber")
    @Mapping(target = "toSerialNumber", source = "toAccount.serialNumber")
    MovementDtoCreated entityToDtoCreated(MovementEntity movementEntity);



}
