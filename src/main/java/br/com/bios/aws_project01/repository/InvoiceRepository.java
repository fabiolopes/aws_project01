package br.com.bios.aws_project01.repository;

import br.com.bios.aws_project01.model.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findAllByCustomerName(String customerName);
}
