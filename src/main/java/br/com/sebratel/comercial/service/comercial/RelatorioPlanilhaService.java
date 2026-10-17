package br.com.sebratel.comercial.service.comercial;

import br.com.sebratel.comercial.dto.comercial.PlanilhaInstalacaoDTO;
import br.com.sebratel.comercial.repository.erp.comercial.RelatorioPlanilhaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RelatorioPlanilhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelatorioPlanilhaService.class);

    private final RelatorioPlanilhaRepository repository;

    @Transactional(readOnly = true)
    public Stream<PlanilhaInstalacaoDTO> listarPlanilhaInstalacao(String nome) {
        LOGGER.info("------- Listando Planilha Instalacao -------");
        return repository.findPlanilhaInstalacao(nome.trim().toUpperCase())
                .map(PlanilhaInstalacaoDTO::new);
    }
}