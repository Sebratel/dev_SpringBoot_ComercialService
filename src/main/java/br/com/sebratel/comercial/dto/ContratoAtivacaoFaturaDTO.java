package br.com.sebratel.comercial.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContratoAtivacaoFaturaDTO(
        String contrato,
        String vendedor,
        String nome,
        LocalDateTime dataAtivacao,
        LocalDate vencimento
) {}