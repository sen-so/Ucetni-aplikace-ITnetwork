package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceStatistics;
import cz.itnetwork.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/invoices")
    public List<InvoiceDTO> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/invoices/{invoice_id}")
    public InvoiceDTO getInvoiceDetail(@PathVariable Long invoice_id) {
        return invoiceService.getInvoiceDetail(invoice_id);
    }

    @PutMapping("/invoices/{invoice_id}")
    public InvoiceDTO editInvoice(@PathVariable Long invoice_id, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.updateInvoice(invoice_id, invoiceDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("invoices/{invoice_id}")
    public void removeInvoice(@PathVariable Long invoice_id) {
        invoiceService.removeInvoice(invoice_id);

    }

    @GetMapping("/invoices/statistics")
    public InvoiceStatistics getInvoiceStatistics() {
        return invoiceService.getInvoiceStatistics();
    }

}
