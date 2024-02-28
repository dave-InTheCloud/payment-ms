package lu.dave.finance.payment.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapter;

@MapperConfig(componentModel = "spring", uses = ConversionServiceAdapter.class)
//@SpringMapperConfig(conversionServiceAdapterPackage = "org.mapstruct.extensions.spring.converter.ConversionServiceAdapter")
public interface MapperSpringConfig {
}