package br.com.sebratel.comercial.controller.scripts;

import br.com.sebratel.comercial.dto.comercial.RelatorioPorVendedorDTO;
import br.com.sebratel.comercial.service.comercial.PrimeiroPaganteMensalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/reports", "/api/v1/primeiro-pagante-mensal", "/api/v1/relatorios"})
public class FirstMonthlyPayerController {


    private final PrimeiroPaganteMensalService primeiroPaganteMensalService;

    @Autowired
    private FirstMonthlyPayerController(PrimeiroPaganteMensalService primeiroPaganteMensalService) {
        this.primeiroPaganteMensalService = primeiroPaganteMensalService;
    }

    @GetMapping({"/first-monthly-payer", "/primeiro-pagante-mensal"})
    public ResponseEntity<List<RelatorioPorVendedorDTO>> execute() {
        List<RelatorioPorVendedorDTO> response = primeiroPaganteMensalService.filtroELoop();
        return ResponseEntity.ok(response);
    }
}
