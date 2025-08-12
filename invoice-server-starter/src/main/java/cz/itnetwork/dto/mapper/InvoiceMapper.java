package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for converting between {@link InvoiceEntity} and {@link InvoiceDTO}.
 *
 * <p>Uses the MapStruct library to automatically generate mapping implementations
 * for transforming data between the persistence layer (entities) and the API layer (DTOs).</p>
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     * Converts an {@link InvoiceDTO} to an {@link InvoiceEntity}.
     *
     * <p>Used to transform API-layer objects (DTO) into a database-ready
     * persistence model (Entity).</p>
     *
     * @param source the {@link InvoiceDTO} to convert
     * @return the mapped {@link InvoiceEntity}
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Converts an {@link InvoiceEntity} to an {@link InvoiceDTO}.
     *
     * <p>Used to transform database entities into API-ready DTO objects.</p>
     *
     * @param source the {@link InvoiceEntity} to convert
     * @return the mapped {@link InvoiceDTO}
     */
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")
    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     * Updates an existing {@link InvoiceEntity} with values from an {@link InvoiceDTO}.
     *
     * <p>This method is used when updating entity data (e.g., fetched from the database)
     * directly with values from a DTO, without creating a new entity instance.</p>
     *
     * @param source the source {@link InvoiceDTO} containing updated data
     * @param entity the target {@link InvoiceEntity} to update
     */
    void updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity entity);
}
