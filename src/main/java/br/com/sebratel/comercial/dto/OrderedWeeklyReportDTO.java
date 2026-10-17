package br.com.sebratel.comercial.dto;

import java.util.List;

public record OrderedWeeklyReportDTO(
        List<RelatorioFinalDTO> data
) {}