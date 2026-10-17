package br.com.sebratel.comercial.repository.erp;

import br.com.sebratel.comercial.repository.erp.projections.ContractActivationProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractActivationRepositoryTest {

    @Mock
    private ContractActivationRepository repository;

    @Test
    @DisplayName("Deve validar o retorno dos dados mesclados de ativação via mock")
    void findMergedContractDataSucesso() {
        ContractActivationProjection mockProjection = createMockContractProjection();
        when(repository.findMergedContractData()).thenReturn(List.of(mockProjection));
        List<ContractActivationProjection> result = repository.findMergedContractData();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        ContractActivationProjection projection = result.get(0);
        assertThat(projection.getContrato()).isEqualTo("CTR-999");
        assertThat(projection.getClientes()).isEqualTo("Cliente Teste");

    }


    private ContractActivationProjection createMockContractProjection() {
        ContractActivationProjection m = mock(ContractActivationProjection.class);

        when(m.getContrato()).thenReturn("CTR-999");
        when(m.getClientes()).thenReturn("Cliente Teste");
        return m;
    }
}