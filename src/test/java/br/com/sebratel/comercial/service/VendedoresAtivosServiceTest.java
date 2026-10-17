package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.VendedoresAtivosDTO;
import br.com.sebratel.comercial.repository.erp.VendedoresAtivosRepository;
import br.com.sebratel.comercial.repository.erp.projections.VendedoresProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendedoresAtivosServiceTest {

    @Mock
    private VendedoresAtivosRepository vendedorRepository;

    @InjectMocks
    private VendedoresAtivosService service;

    @Test
    void deveListarVendedoresAtivosComSucesso() {
        // GIVEN
        // Assumindo que o repositório retorna uma Projeção ou Entity compatível com o construtor do DTO
        VendedoresProjection vendedoresProjection = mock(VendedoresProjection.class);
        when(vendedorRepository.findVendedoresAtivos()).thenReturn(List.of(vendedoresProjection));

        // WHEN
        List<VendedoresAtivosDTO> resultado = service.listarVendedoresAtivos();

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(vendedorRepository, times(1)).findVendedoresAtivos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverVendedores() {
        // GIVEN
        when(vendedorRepository.findVendedoresAtivos()).thenReturn(List.of());

        // WHEN
        List<VendedoresAtivosDTO> resultado = service.listarVendedoresAtivos();

        // THEN
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(vendedorRepository, times(1)).findVendedoresAtivos();
    }

    @Test
    void deveVerificarSeChamadaAoRepositorioOcorreUmaVez() {
        // GIVEN
        when(vendedorRepository.findVendedoresAtivos()).thenReturn(List.of());

        // WHEN
        service.listarVendedoresAtivos();

        // THEN
        verify(vendedorRepository, only()).findVendedoresAtivos();
    }
}