package br.com.sebratel.comercial.repository.erp.projections;

public interface ContratoSemFaturaProjection {
    String getContractDescription();
    String getPppoe();
    Long getQtdFaturas();
}