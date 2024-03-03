package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.AccountDtoWithCustomer;
import lu.dave.finance.payment.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface AccountMapper extends Converter<AccountDto, AccountEntity> {

    AccountEntity convert(AccountDto accountDto);

    @Mapping(source = "customer.id", target = "ownerId")
    AccountDtoWithCustomer convert(AccountEntity accountEntity);

    List<AccountDtoWithCustomer> convert(List<AccountEntity> all);
}
