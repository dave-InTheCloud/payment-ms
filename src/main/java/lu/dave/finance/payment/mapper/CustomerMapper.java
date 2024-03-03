package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.CustomerDto;
import lu.dave.finance.payment.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapperSpringConfig.class)
public interface CustomerMapper  extends Converter<CustomerDto, CustomerEntity>  {
    @Override
    CustomerEntity convert( CustomerDto customerDto);

    CustomerDto convert(CustomerEntity customerEntity);

}
