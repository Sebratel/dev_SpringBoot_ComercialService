package br.com.sebratel.comercial.service;

import br.com.sebratel.comercial.dto.RelatorioFinalDTO;
import br.com.sebratel.comercial.repository.erp.ContractActivationRepository;
import br.com.sebratel.comercial.repository.erp.projections.ContractActivationProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractDataServiceTest {

    @Mock
    private ContractActivationRepository repository;

    @InjectMocks
    private ContractDataService service;

    @Test
    void getDadosCompletosCacheShouldReturnMappedDTOs() {
        // Arrange
        ContractActivationProjection p = mock(ContractActivationProjection.class);
        LocalDateTime date = LocalDateTime.of(2023, 10, 5, 10, 0); // October -> Outubro
        
        when(p.getClientes()).thenReturn("Client A");
        when(p.getContrato()).thenReturn("123");
        when(p.getDataCriacaoContrato()).thenReturn(date);
        when(p.getDataAtivacao()).thenReturn(date.plusDays(1));
        when(p.getVendedor()).thenReturn("Seller X");
        when(p.getTecnologia()).thenReturn("Fiber");
        when(p.getStatusContrato()).thenReturn("Active");
        when(p.getStatusCancelamento()).thenReturn("N/A");

        when(repository.findMergedContractData()).thenReturn(List.of(p));

        // Act
        List<RelatorioFinalDTO> result = service.getDadosCompletosCache();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        RelatorioFinalDTO dto = result.get(0);
        assertEquals("Client A", dto.clientes());
        assertEquals("Outubro", dto.mesDaCriacao());
        verify(repository, times(1)).findMergedContractData();
    }

    @Test
    void getDadosCompletosCacheShouldHandleDuplicateKeys() {
        // Arrange
        ContractActivationProjection p1 = mock(ContractActivationProjection.class);
        when(p1.getClientes()).thenReturn("Client A");
        when(p1.getContrato()).thenReturn("123");
        
        ContractActivationProjection p2 = mock(ContractActivationProjection.class);
        when(p2.getClientes()).thenReturn("Client A");
        when(p2.getContrato()).thenReturn("123");

        when(repository.findMergedContractData()).thenReturn(List.of(p1, p2));

        // Act
        List<RelatorioFinalDTO> result = service.getDadosCompletosCache();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getDadosCompletosCacheShouldReturnEmptyListWhenNoData() {
        // Arrange
        when(repository.findMergedContractData()).thenReturn(List.of());

        // Act
        List<RelatorioFinalDTO> result = service.getDadosCompletosCache();

        // Assert
        assertTrue(result.isEmpty());
    }
}
