
package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper pro převod mezi PersonEntity a PersonDTO
 * *
 * Používá knihovnu Mapstructur pro automatické generování
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    /**
     * Převede objekt PersonDTO na PersonEntity.
     * *
     * Slouží k transformaci dat z formátu používaného ve vrstvě API(DTO)
     * do formátu vhodného pro uložení do databáze (Entity)
     *
     * @param source - vstupní objekt typu PersonDTO
     * @return - převedený objekt typu PersonEntity
     */
    PersonEntity toEntity(PersonDTO source);

    /**
     * Převede objekt PersonEntity na PersonDTO.
     * *
     * Slouží k transformaci dat z databázové entity
     * do formátu vhodného pro přenos přes API (DTO).
     *
     * @param source vstupní objekt typu PersonEntity
     * @return převedený objekt typu PersonDTO
     */

    PersonDTO toDTO(PersonEntity source);

    /**
     * Aktualizuje existující InvoiceEntity hodnotami z PersonDTO.
     * *
     * Používá se, když chceme změnit data entity (např. z databáze)
     * bez vytváření nového objektu, přímo podle dat z DTO
     *
     * @param source - zdrojový objekt PersonDTO s novými daty
     * @param person - cílový objekt PersonEntity, který bude aktualizován
     */
    void updateEntity(PersonDTO source, @MappingTarget PersonEntity person);
}
