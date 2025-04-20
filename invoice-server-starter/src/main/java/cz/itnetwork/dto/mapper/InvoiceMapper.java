package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     *
     * @param source
     * @return
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     *
     * @param source
     * @return
     */
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")
    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     *
     * @param source
     * @param entity
     */
    void updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity entity);
}
