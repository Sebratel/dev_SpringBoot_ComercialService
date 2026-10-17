package br.com.sebratel.comercial.controller.scripts;

import br.com.sebratel.comercial.dto.OrderedWeeklyReportDTO;
import br.com.sebratel.comercial.dto.RelatorioFinalDTO;
import br.com.sebratel.comercial.dto.ActiveSellersInputDTO;
import br.com.sebratel.comercial.service.WeeklyReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/weekly-reports", "/api/v1/apoio-semanal"})
@Slf4j
public class WeeklyReportController {


    private final WeeklyReportService weeklyReportService;

    @Autowired
    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @GetMapping(value = "/vendedor")
    public ResponseEntity<OrderedWeeklyReportDTO> getPorVendedor(@RequestBody ActiveSellersInputDTO activeSellersInputDTO) {
        log.info("Iniciando relatorio para {}", activeSellersInputDTO.getNome());
        List<RelatorioFinalDTO> list = weeklyReportService.sellersReportStream(activeSellersInputDTO.getNome()).toList();
        return ResponseEntity.ok(new OrderedWeeklyReportDTO(list));
    }
}