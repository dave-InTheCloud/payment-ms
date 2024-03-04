package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.dto.CustomerDtoRequest;
import lu.dave.finance.payment.dto.CustomerDtoWithAccount;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(config = MapperSpringConfig.class)
public interface CustomerMapper  extends Converter<CustomerDtoRequest, CustomerEntity>  {
    @Override
    CustomerEntity convert( CustomerDtoRequest customerDtoRequest);

    CustomerDto convert(CustomerEntity customerEntity);

    List<CustomerDtoWithAccount> convert(List<CustomerEntity> customerEntity);

}
