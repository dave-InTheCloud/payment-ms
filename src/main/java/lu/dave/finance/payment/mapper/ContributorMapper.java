package lu.dave.finance.payment.mapper;

import lu.dave.finance.payment.config.MapperSpringConfig;
import lu.dave.finance.payment.dto.ContributorDto;
import lu.dave.finance.payment.entity.ContributorEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MapperSpringConfig.class)
public interface ContributorMapper extends Converter<ContributorDto, ContributorEntity>  {
    @Override
    ContributorEntity convert(ContributorDto contributorDto);


    //@Mapping(source = "contributorEntity.customerId")
    ContributorDto convert(ContributorEntity contributorEntity);
}
