package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.ContratoSemFaturaDTO;
import br.com.sebratel.comercial.repository.erp.ContratoSemFaturaRepository;
import br.com.sebratel.comercial.repository.erp.projections.ContratoSemFaturaProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class ContratoSemFaturaServiceTest {

    @Mock
    private ContratoSemFaturaRepository repository;

    @InjectMocks
    private ContratoSemFaturaService service;

    @Test
    void deveListarContratosSemFaturaComSucesso() {
        ContratoSemFaturaProjection p = mock(ContratoSemFaturaProjection.class);

        when(p.getContractDescription()).thenReturn("Contrato Teste 123");
        when(p.getPppoe()).thenReturn("usuario@teste");
        when(p.getQtdFaturas()).thenReturn(0L);

        when(repository.findContratosSemFatura()).thenReturn(List.of(p));

        List<ContratoSemFaturaDTO> resultado = service.listarContratosSemFatura();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        ContratoSemFaturaDTO dto = resultado.get(0);
        assertEquals("Contrato Teste 123", dto.contractDescription());
        assertEquals("usuario@teste", dto.pppoe());
        assertEquals(0, dto.qtdFaturas());

        verify(repository, times(1)).findContratosSemFatura();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDados() {
        when(repository.findContratosSemFatura()).thenReturn(List.of());

        List<ContratoSemFaturaDTO> resultado = service.listarContratosSemFatura();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findContratosSemFatura();
    }

    @Test
    void deveMapearCamposNulosCorretamente() {
        ContratoSemFaturaProjection p = mock(ContratoSemFaturaProjection.class);
        when(p.getContractDescription()).thenReturn(null);
        when(p.getPppoe()).thenReturn(null);
        when(p.getQtdFaturas()).thenReturn(null);

        when(repository.findContratosSemFatura()).thenReturn(List.of(p));

        List<ContratoSemFaturaDTO> resultado = service.listarContratosSemFatura();

        assertNotNull(resultado);
        assertNull(resultado.get(0).contractDescription());
        assertNull(resultado.get(0).pppoe());
        assertNull(resultado.get(0).qtdFaturas());
    }
}