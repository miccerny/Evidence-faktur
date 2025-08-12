package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper pro převod mezi InvoiceEntiy a InvoiceDTO
 * *
 * Používá knihovnu Mapstructur pro automatické generování
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     * Převede objekt InvoiceDTO na InvoiceEntity.
     * *
     * Slouží k transformaci dat z formátu používaného ve vrstvě API(DTO)
     * do formátu vhodného pro uložení do databáze (Entity)
     *
     * @param source - vstupní objekt typu InvoiceDTO
     * @return - převedený objekt typu InvoiceEntity
     */
    InvoiceEntity toEntity(InvoiceDTO source);

    /**
     * Převede objekt typu InvoiceEntity na InvoiceDTO
     * *
     *  Používá se k transformaci dat z entity (databázového modelu)
     *  do DTO (objektu používaného v API).
     *
     * @param source - vstupní objekt typu InvoiceEntity
     * @return - převedený objekt typu InvoiceDTO
     */
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")

    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     * Aktualizuje existující InvoiceEntity hodnotami z InvoiceDTO.
     *
     * Používá se, když chceme změnit data entity (např. z databáze)
     * bez vytváření nového objektu, přímo podle dat z DTO
     *
     * @param source - zdrojový objekt InvoiceDTO s novými daty
     * @param entity - cílový objekt InvoiceEntity, který bude aktualizován
     */
    void updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity entity);
}
