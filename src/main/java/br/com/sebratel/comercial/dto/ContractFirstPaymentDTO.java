package br.com.sebratel.comercial.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
public class ContractFirstPaymentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nome;
    private String numeroContrato;
    private LocalDate primeiraEmissao;
    private LocalDate pagamentoCliente;
    private LocalDateTime dataCriacao;
    private String contractNumber;
    private String description;
    private String status;

    // Construtor, Getters e Setters
}