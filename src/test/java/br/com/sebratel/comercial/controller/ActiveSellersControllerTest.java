package br.com.sebratel.comercial.controller;

import br.com.sebratel.comercial.BaseTest;
import br.com.sebratel.comercial.controller.scripts.ActiveSellersController;
import br.com.sebratel.comercial.dto.VendedoresAtivosDTO;
import br.com.sebratel.comercial.service.VendedoresAtivosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActiveSellersController.class)
@AutoConfigureMockMvc(addFilters = false)
class ActiveSellersControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VendedoresAtivosService vendedoresAtivosService;

    @Test
    @DisplayName("Should return 200 when listing active sellers")
    void getActiveSellersSuccess() throws Exception {
        VendedoresAtivosDTO dto = new VendedoresAtivosDTO(
                "", ""
        );
        List<VendedoresAtivosDTO> list = List.of(dto);

        when(vendedoresAtivosService.listarVendedoresAtivos()).thenReturn(list);

        mockMvc.perform(get("/api/v1/sellers/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
