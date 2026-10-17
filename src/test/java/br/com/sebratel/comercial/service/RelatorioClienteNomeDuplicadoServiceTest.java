package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.RelatorioClienteNomeDuplicadoDTO;
import br.com.sebratel.comercial.repository.erp.RelatorioClienteNomeDuplicadoRepository;
import br.com.sebratel.comercial.repository.erp.projections.RelatorioClienteNomeDuplicadoProjection;
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
class RelatorioClienteNomeDuplicadoServiceTest {

    @Mock
    private RelatorioClienteNomeDuplicadoRepository repository;

    @InjectMocks
    private RelatorioClienteNomeDuplicadoService service;

    @Test
    void deveListarClientesNomesDuplicadosComSucesso() {
        // GIVEN
        RelatorioClienteNomeDuplicadoProjection p = mock(RelatorioClienteNomeDuplicadoProjection.class);
        when(p.getAuthenticatedUser()).thenReturn("user.teste");
        when(p.getAuthContractDescription()).thenReturn("Contrato XPTO");
        when(p.getEventDescription()).thenReturn("Evento de Duplicidade");

        when(repository.findClientesNomesDuplicados()).thenReturn(List.of(p));

        // WHEN
        List<RelatorioClienteNomeDuplicadoDTO> resultado = service.listarClientesNomesDuplicados();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        RelatorioClienteNomeDuplicadoDTO dto = resultado.get(0);
        assertEquals("user.teste", dto.authenticatedUser());
        assertEquals("Contrato XPTO", dto.authContractDescription());
        assertEquals("Evento de Duplicidade", dto.eventDescription());

        verify(repository, times(1)).findClientesNomesDuplicados();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverNomesDuplicados() {
        // GIVEN
        when(repository.findClientesNomesDuplicados()).thenReturn(List.of());

        // WHEN
        List<RelatorioClienteNomeDuplicadoDTO> resultado = service.listarClientesNomesDuplicados();

        // THEN
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findClientesNomesDuplicados();
    }

    @Test
    void deveMapearCamposNulosCorretamente() {
        // GIVEN
        RelatorioClienteNomeDuplicadoProjection p = mock(RelatorioClienteNomeDuplicadoProjection.class);
        when(p.getAuthenticatedUser()).thenReturn(null);
        when(p.getAuthContractDescription()).thenReturn(null);
        when(p.getEventDescription()).thenReturn(null);

        when(repository.findClientesNomesDuplicados()).thenReturn(List.of(p));

        // WHEN
        List<RelatorioClienteNomeDuplicadoDTO> resultado = service.listarClientesNomesDuplicados();

        // THEN
        assertNotNull(resultado);
        assertNull(resultado.get(0).authenticatedUser());
        assertNull(resultado.get(0).authContractDescription());
        assertNull(resultado.get(0).eventDescription());
    }
}