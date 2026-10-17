package br.com.sebratel.comercial.dto;

public record RelatorioClienteNomeDuplicadoDTO(
        String authenticatedUser,
        String authContractDescription,
        String eventDescription
) {}