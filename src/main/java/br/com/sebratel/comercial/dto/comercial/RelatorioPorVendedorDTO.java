package br.com.sebratel.comercial.dto.comercial;

import br.com.sebratel.comercial.dto.VendedoresAtivosDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class RelatorioPorVendedorDTO {
    VendedoresAtivosDTO dadosDoVendedor;
    List<PrimeiroPaganteMensalDTO> relatorioPorVendedor;
}
