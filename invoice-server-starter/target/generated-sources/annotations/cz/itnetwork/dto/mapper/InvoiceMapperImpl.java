package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.14 (JetBrains s.r.o.)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public InvoiceEntity toEntity(InvoiceDTO source) {
        if ( source == null ) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        if ( source.getId() != null ) {
            invoiceEntity.setId( source.getId() );
        }
        invoiceEntity.setInvoiceNumber( source.getInvoiceNumber() );
        invoiceEntity.setSeller( personDTOToPersonEntity( source.getSeller() ) );
        invoiceEntity.setBuyer( personDTOToPersonEntity( source.getBuyer() ) );
        invoiceEntity.setIssued( source.getIssued() );
        invoiceEntity.setDueDate( source.getDueDate() );
        invoiceEntity.setProduct( source.getProduct() );
        invoiceEntity.setPrice( BigDecimal.valueOf( source.getPrice() ) );
        invoiceEntity.setVat( (int) source.getVat() );
        invoiceEntity.setNote( source.getNote() );

        return invoiceEntity;
    }

    @Override
    public InvoiceDTO toDTO(InvoiceEntity source) {
        if ( source == null ) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setSeller( personEntityToPersonDTO( source.getSeller() ) );
        invoiceDTO.setBuyer( personEntityToPersonDTO( source.getBuyer() ) );
        invoiceDTO.setId( source.getId() );
        invoiceDTO.setInvoiceNumber( source.getInvoiceNumber() );
        invoiceDTO.setIssued( source.getIssued() );
        invoiceDTO.setDueDate( source.getDueDate() );
        invoiceDTO.setProduct( source.getProduct() );
        if ( source.getPrice() != null ) {
            invoiceDTO.setPrice( source.getPrice().floatValue() );
        }
        invoiceDTO.setVat( source.getVat() );
        invoiceDTO.setNote( source.getNote() );

        return invoiceDTO;
    }

    @Override
    public void updateEntity(InvoiceDTO source, InvoiceEntity entity) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            entity.setId( source.getId() );
        }
        entity.setInvoiceNumber( source.getInvoiceNumber() );
        if ( source.getSeller() != null ) {
            if ( entity.getSeller() == null ) {
                entity.setSeller( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getSeller(), entity.getSeller() );
        }
        else {
            entity.setSeller( null );
        }
        if ( source.getBuyer() != null ) {
            if ( entity.getBuyer() == null ) {
                entity.setBuyer( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getBuyer(), entity.getBuyer() );
        }
        else {
            entity.setBuyer( null );
        }
        entity.setIssued( source.getIssued() );
        entity.setDueDate( source.getDueDate() );
        entity.setProduct( source.getProduct() );
        entity.setPrice( BigDecimal.valueOf( source.getPrice() ) );
        entity.setVat( (int) source.getVat() );
        entity.setNote( source.getNote() );
    }

    protected PersonEntity personDTOToPersonEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        if ( personDTO.getId() != null ) {
            personEntity.setId( personDTO.getId() );
        }
        personEntity.setName( personDTO.getName() );
        personEntity.setIdentificationNumber( personDTO.getIdentificationNumber() );
        personEntity.setTaxNumber( personDTO.getTaxNumber() );
        personEntity.setAccountNumber( personDTO.getAccountNumber() );
        personEntity.setBankCode( personDTO.getBankCode() );
        personEntity.setIban( personDTO.getIban() );
        personEntity.setTelephone( personDTO.getTelephone() );
        personEntity.setMail( personDTO.getMail() );
        personEntity.setStreet( personDTO.getStreet() );
        personEntity.setZip( personDTO.getZip() );
        personEntity.setCity( personDTO.getCity() );
        personEntity.setCountry( personDTO.getCountry() );
        personEntity.setNote( personDTO.getNote() );

        return personEntity;
    }

    protected PersonDTO personEntityToPersonDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId( personEntity.getId() );
        personDTO.setName( personEntity.getName() );
        personDTO.setIdentificationNumber( personEntity.getIdentificationNumber() );
        personDTO.setTaxNumber( personEntity.getTaxNumber() );
        personDTO.setAccountNumber( personEntity.getAccountNumber() );
        personDTO.setBankCode( personEntity.getBankCode() );
        personDTO.setIban( personEntity.getIban() );
        personDTO.setTelephone( personEntity.getTelephone() );
        personDTO.setMail( personEntity.getMail() );
        personDTO.setStreet( personEntity.getStreet() );
        personDTO.setZip( personEntity.getZip() );
        personDTO.setCity( personEntity.getCity() );
        personDTO.setCountry( personEntity.getCountry() );
        personDTO.setNote( personEntity.getNote() );

        return personDTO;
    }

    protected void personDTOToPersonEntity1(PersonDTO personDTO, PersonEntity mappingTarget) {
        if ( personDTO == null ) {
            return;
        }

        if ( personDTO.getId() != null ) {
            mappingTarget.setId( personDTO.getId() );
        }
        mappingTarget.setName( personDTO.getName() );
        mappingTarget.setIdentificationNumber( personDTO.getIdentificationNumber() );
        mappingTarget.setTaxNumber( personDTO.getTaxNumber() );
        mappingTarget.setAccountNumber( personDTO.getAccountNumber() );
        mappingTarget.setBankCode( personDTO.getBankCode() );
        mappingTarget.setIban( personDTO.getIban() );
        mappingTarget.setTelephone( personDTO.getTelephone() );
        mappingTarget.setMail( personDTO.getMail() );
        mappingTarget.setStreet( personDTO.getStreet() );
        mappingTarget.setZip( personDTO.getZip() );
        mappingTarget.setCity( personDTO.getCity() );
        mappingTarget.setCountry( personDTO.getCountry() );
        mappingTarget.setNote( personDTO.getNote() );
    }
}
