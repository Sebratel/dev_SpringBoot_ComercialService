package br.com.sebratel.comercial.dto;

import java.time.LocalDate;

public record ContratoBloqueadoDTO(
        String cliente,
        String contrato,
        String usuario,
        String concentrador,
        String pontoAcesso,
        String statusContrato,
        String estagioContrato,
        String site,
        String statusConexao,
        String splitter,
        String cidade,
        LocalDate diaBloqueio,
        Integer diasBloqueados
) {}