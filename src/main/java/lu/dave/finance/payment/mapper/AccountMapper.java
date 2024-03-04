package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.AccountDtoRequest;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithChildren;
import lu.dave.finance.payment.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface AccountMapper extends Converter<AccountDtoRequest, AccountEntity> {

    AccountEntity convert(AccountDtoRequest accountDtoRequest);

    @Mapping(source = "customer.id", target = "ownerId")
    AccountDto convert(AccountEntity accountEntity);

    List<AccountDto> convert(List<AccountEntity> all);

    @Mapping(source = "customer.id", target = "ownerId")
    AccountDtoWithChildren convertWithChildren(AccountEntity accountEntity);

    List<AccountDtoWithChildren> convertWithChildren(List<AccountEntity> accountEntity);

}
