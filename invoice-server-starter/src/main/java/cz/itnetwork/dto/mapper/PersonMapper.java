
package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for converting between {@link PersonEntity} and {@link PersonDTO}.
 *
 * <p>Uses the MapStruct library to automatically generate mapping implementations
 * for transforming data between the persistence layer (entities) and the API layer (DTOs).</p>
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    /**
     * Converts a {@link PersonDTO} to a {@link PersonEntity}.
     *
     * <p>Used to transform API-layer objects (DTO) into a database-ready
     * persistence model (Entity).</p>
     *
     * @param source the {@link PersonDTO} to convert
     * @return the mapped {@link PersonEntity}
     */
    PersonEntity toEntity(PersonDTO source);

    /**
     * Converts a {@link PersonEntity} to a {@link PersonDTO}.
     *
     * <p>Used to transform database entities into API-ready DTO objects.</p>
     *
     * @param source the {@link PersonEntity} to convert
     * @return the mapped {@link PersonDTO}
     */
    PersonDTO toDTO(PersonEntity source);

    /**
     * Updates an existing {@link PersonEntity} with values from a {@link PersonDTO}.
     *
     * <p>This method is used when updating entity data (e.g., fetched from the database)
     * directly with values from a DTO, without creating a new entity instance.</p>
     *
     * @param source the source {@link PersonDTO} containing updated data
     * @param person the target {@link PersonEntity} to update
     */
    void updateEntity(PersonDTO source, @MappingTarget PersonEntity person);
}
