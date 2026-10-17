package br.com.sebratel.comercial.controller;

import br.com.sebratel.comercial.BaseTest;
import br.com.sebratel.comercial.controller.scripts.WeeklyReportController;
import br.com.sebratel.comercial.dto.RelatorioFinalDTO;
import br.com.sebratel.comercial.dto.ActiveSellersInputDTO;
import br.com.sebratel.comercial.service.WeeklyReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeeklyReportController.class)
@AutoConfigureMockMvc(addFilters = false)
class WeeklyReportControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WeeklyReportService apoioSemanalService;

    @Test
    @DisplayName("Deve retornar 200 ao buscar dados do vendedor")
    void getPorVendedorSucesso() throws Exception {
        ActiveSellersInputDTO input = new ActiveSellersInputDTO();
        input.setNome("nome");

        RelatorioFinalDTO dto = new RelatorioFinalDTO(
                null,
                null,
                "v3",
                "v4",
                "v5",
                "v6",
                "v7",
                "v8",
                "v9"
        );
        when(apoioSemanalService.sellersReportStream(anyString()))
                .thenReturn(Stream.of(dto));

        mockMvc.perform(get("/api/v1/apoio-semanal/vendedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o corpo da requisição é inválido")
    void getPorVendedorBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/apoio-semanal/vendedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }
}