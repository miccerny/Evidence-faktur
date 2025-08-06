package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for converting between InvoiceEntity and InvoiceDTO.
 * <p>
 * Uses the MapStruct library for automatic code generation.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     * Converts an InvoiceDTO object to an InvoiceEntity.
     * <p>
     * Used to transform data from the format used in the API layer (DTO)
     * to the format suitable for database storage (Entity).
     *
     * @param source - input object of type InvoiceDTO
     * @return - converted object of type InvoiceEntity
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Converts an InvoiceEntity object to InvoiceDTO.
     * <p>
     * Used to transform data from the entity (database model)
     * to a DTO (object used in the API).
     *
     * @param source - input object of type InvoiceEntity
     * @return - converted object of type InvoiceDTO
     */
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")
    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     * Updates an existing InvoiceEntity with values from InvoiceDTO.
     * <p>
     * Used when you want to update the entity's data (e.g., from the database)
     * directly with values from a DTO, without creating a new object.
     *
     * @param source - source InvoiceDTO object with new data
     * @param entity - target InvoiceEntity object to be updated
     */
    void updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity entity);
}
