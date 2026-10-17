package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.ContratoBloqueadoDTO;
import br.com.sebratel.comercial.repository.erp.ContratoBloqueadoRepository;
import br.com.sebratel.comercial.repository.erp.projections.ContratoBloqueadoProjection;
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
class ContratoBloqueadoServiceTest {

    @Mock
    private ContratoBloqueadoRepository repository;

    @InjectMocks
    private ContratoBloqueadoService service;

    @Test
    void deveListarContratosBloqueadosComMapeamentoCompleto() {
        ContratoBloqueadoProjection p = mock(ContratoBloqueadoProjection.class);

        LocalDate mockData = LocalDate.now();

        when(p.getCliente()).thenReturn("Cliente Bloqueado");
        when(p.getContrato()).thenReturn("5005");
        when(p.getUsuario()).thenReturn("user.bloqueio");
        when(p.getConcentrador()).thenReturn("Concentrador 01");
        when(p.getPontoAcesso()).thenReturn("Ponto A");
        when(p.getStatusContrato()).thenReturn("Bloqueado Financeiro");
        when(p.getEstagioContrato()).thenReturn("Estágio 2");
        when(p.getSite()).thenReturn("Site Principal");
        when(p.getStatusConexao()).thenReturn("OFFLINE");
        when(p.getSplitter()).thenReturn("Splitter 05");
        when(p.getCidade()).thenReturn("Cidade Teste");
        when(p.getDiaBloqueio()).thenReturn(mockData);
        when(p.getDiasBloqueados()).thenReturn(10);

        when(repository.findContratosBloqueados()).thenReturn(List.of(p));

        List<ContratoBloqueadoDTO> resultado = service.listarContratosBloqueados();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        ContratoBloqueadoDTO dto = resultado.get(0);
        assertEquals("Cliente Bloqueado", dto.cliente());
        assertEquals("5005", dto.contrato());
        assertEquals("user.bloqueio", dto.usuario());
        assertEquals("Concentrador 01", dto.concentrador());
        assertEquals("Ponto A", dto.pontoAcesso());
        assertEquals("Bloqueado Financeiro", dto.statusContrato());
        assertEquals("Estágio 2", dto.estagioContrato());
        assertEquals("Site Principal", dto.site());
        assertEquals("OFFLINE", dto.statusConexao());
        assertEquals("Splitter 05", dto.splitter());
        assertEquals("Cidade Teste", dto.cidade());
        assertEquals(mockData, dto.diaBloqueio());
        assertEquals(10, dto.diasBloqueados());

        verify(repository, times(1)).findContratosBloqueados();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverContratosBloqueados() {
        when(repository.findContratosBloqueados()).thenReturn(List.of());

        List<ContratoBloqueadoDTO> resultado = service.listarContratosBloqueados();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findContratosBloqueados();
    }

    @Test
    void deveMapearCamposNulosCorretamente() {
        ContratoBloqueadoProjection p = mock(ContratoBloqueadoProjection.class);
        when(p.getCliente()).thenReturn(null);
        when(p.getContrato()).thenReturn(null);

        when(repository.findContratosBloqueados()).thenReturn(List.of(p));

        List<ContratoBloqueadoDTO> resultado = service.listarContratosBloqueados();

        assertNotNull(resultado);
        assertNull(resultado.get(0).cliente());
        assertNull(resultado.get(0).contrato());
    }
}