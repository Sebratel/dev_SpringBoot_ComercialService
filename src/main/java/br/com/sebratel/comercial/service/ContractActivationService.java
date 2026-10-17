package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.ContractActivationDTO;
import br.com.sebratel.comercial.repository.erp.ContractActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractActivationService {

    @Autowired
    private ContractActivationRepository repository;

    public List<ContractActivationDTO> getActivationReport() {
        return repository.findMergedContractData().stream().map(p -> {
            ContractActivationDTO dto = new ContractActivationDTO();
            dto.setDataCriacaoContrato(p.getDataCriacaoContrato().toString());
            dto.setClientes(p.getClientes());
            dto.setStatusContrato(p.getStatusContrato());
            dto.setStatusCancelamento(p.getStatusCancelamento());
            dto.setContrato(p.getContrato());
            dto.setVendedor(p.getVendedor());
            dto.setTecnologia(p.getTecnologia());
            dto.setDataAtivacao(p.getDataAtivacao().toString());
            return dto;
        }).collect(Collectors.toList());
    }
}