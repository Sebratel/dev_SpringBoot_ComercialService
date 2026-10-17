package br.com.sebratel.comercial.dto;

public record ContratoSemFaturaDTO(
        String contractDescription,
        String pppoe,
        Long qtdFaturas
) {}