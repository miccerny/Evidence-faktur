
package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for converting between PersonEntity and PersonDTO.
 * <p>
 * Uses the MapStruct library for automatic code generation.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    /**
     * Converts a PersonDTO object to PersonEntity.
     * <p>
     * Used to transform data from the format used in the API layer (DTO)
     * to the format suitable for database storage (Entity).
     *
     * @param source - input object of type PersonDTO
     * @return - converted object of type PersonEntity
     */
    PersonEntity toEntity(PersonDTO source);

    /**
     * Converts a PersonEntity object to PersonDTO.
     * <p>
     * Used to transform data from the database entity
     * to the format suitable for transfer over API (DTO).
     *
     * @param source input object of type PersonEntity
     * @return converted object of type PersonDTO
     */
    @Mapping(target = "ownerEmail", source = "owner.email")
    PersonDTO toDTO(PersonEntity source);

    /**
     * Updates an existing PersonEntity with values from PersonDTO.
     * <p>
     * Used when you want to update entity data (e.g., from the database)
     * directly with data from a DTO, without creating a new object.
     *
     * @param source - source PersonDTO object with new data
     * @param person - target PersonEntity object to be updated
     */
    @Mapping(target = "owner", ignore = true)
    void updateEntity(PersonDTO source, @MappingTarget PersonEntity person);
}
