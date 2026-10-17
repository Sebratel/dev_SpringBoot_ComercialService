package br.com.sebratel.comercial.dto;

import br.com.sebratel.comercial.repository.erp.projections.VendedoresProjection;


public record VendedoresAtivosDTO(String nome, String email) {
    public VendedoresAtivosDTO(VendedoresProjection projection) {
        this(projection.getNomeVendedor(), projection.getEmailVendedor());
    }
}