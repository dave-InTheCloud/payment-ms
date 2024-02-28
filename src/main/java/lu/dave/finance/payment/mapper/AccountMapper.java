package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.AccountDto;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.AccountEntity;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapperSpringConfig.class)
public interface AccountMapper extends Converter<AccountDto, AccountEntity>  {

    AccountEntity convert(AccountDto accountDto);

}
