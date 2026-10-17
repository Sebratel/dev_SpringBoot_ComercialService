package br.com.sebratel.comercial.repository.erp.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ContractFirstPaymentProjection {
    String getNome();
    String getNumeroContrato();
    LocalDate getPrimeiraEmissao();
    LocalDate getPagamentoCliente();
    LocalDateTime getDataCriacao();
    String getContractnumber();
    String getDescription();
    String getStatus();
}