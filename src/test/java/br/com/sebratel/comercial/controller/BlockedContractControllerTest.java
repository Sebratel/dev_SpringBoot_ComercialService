package br.com.sebratel.comercial.controller;

import br.com.sebratel.comercial.BaseTest;
import br.com.sebratel.comercial.controller.scripts.BlockedContractController;
import br.com.sebratel.comercial.dto.ContratoBloqueadoDTO;
import br.com.sebratel.comercial.service.ContratoBloqueadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlockedContractController.class)
@AutoConfigureMockMvc(addFilters = false)
class BlockedContractControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContratoBloqueadoService contratoBloqueadoService;

    @Test
    @DisplayName("Should return 200 when listing blocked contracts")
    void getBlockedContractsSuccess() throws Exception {
        ContratoBloqueadoDTO dto = new ContratoBloqueadoDTO("","","","","","","","","", "", "", LocalDate.now(), 1);
        List<ContratoBloqueadoDTO> list = List.of(dto);

        when(contratoBloqueadoService.listarContratosBloqueados()).thenReturn(list);

        mockMvc.perform(get("/api/v1/contracts/blocked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
