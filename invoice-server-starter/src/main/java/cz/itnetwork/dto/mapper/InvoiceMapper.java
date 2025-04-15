package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceEntity toEntity(InvoiceDTO source);

    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")
    InvoiceDTO toDTO(InvoiceEntity source);

    void updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity entity);
}
