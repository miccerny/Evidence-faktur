package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    List<InvoiceEntity> findAll();

    List<InvoiceEntity> findBySellerIdentificationNumber(String identificationNUmber);

    List<InvoiceEntity> findByBuyerIdentificationNumber(String identificationNumber);
}
