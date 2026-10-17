package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.ContratoAtivacaoFaturaDTO;
import br.com.sebratel.comercial.repository.erp.ContratoAtivacaoFaturaRepository;
import br.com.sebratel.comercial.repository.erp.projections.ContratoAtivacaoFaturaProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContratoAtivacaoFaturaServiceTest {

    @Mock
    private ContratoAtivacaoFaturaRepository repository;

    @InjectMocks
    private ContratoAtivacaoFaturaService service;

    @Test
    void deveListarContratosRelacionadosComSucesso() {
        ContratoAtivacaoFaturaProjection p = mock(ContratoAtivacaoFaturaProjection.class);
        LocalDate agora = LocalDate.now();

        when(p.getContrato()).thenReturn("1001");
        when(p.getVendedor()).thenReturn("Vendedor Teste");
        when(p.getNome()).thenReturn("Cliente Teste");
        when(p.getDataAtivacao()).thenReturn(agora.atStartOfDay());
        when(p.getVencimento()).thenReturn(agora.plusMonths(1));

        when(repository.findContratosAtivacaoFatura()).thenReturn(List.of(p));

        List<ContratoAtivacaoFaturaDTO> resultado = service.listarContratosRelacionados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        ContratoAtivacaoFaturaDTO dto = resultado.get(0);
        assertEquals("1001", dto.contrato());
        assertEquals("Vendedor Teste", dto.vendedor());
        assertEquals("Cliente Teste", dto.nome());
        assertEquals(agora, dto.dataAtivacao().toLocalDate());
        assertEquals(agora.plusMonths(1), dto.vencimento());

        verify(repository, times(1)).findContratosAtivacaoFatura();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDados() {
        when(repository.findContratosAtivacaoFatura()).thenReturn(List.of());

        List<ContratoAtivacaoFaturaDTO> resultado = service.listarContratosRelacionados();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findContratosAtivacaoFatura();
    }

    @Test
    void deveMapearCamposNulosCorretamente() {
        ContratoAtivacaoFaturaProjection p = mock(ContratoAtivacaoFaturaProjection.class);
        when(p.getContrato()).thenReturn(null);
        when(p.getVendedor()).thenReturn(null);

        when(repository.findContratosAtivacaoFatura()).thenReturn(List.of(p));

        List<ContratoAtivacaoFaturaDTO> resultado = service.listarContratosRelacionados();

        assertNotNull(resultado);
        assertNull(resultado.get(0).contrato());
        assertNull(resultado.get(0).vendedor());
    }
}