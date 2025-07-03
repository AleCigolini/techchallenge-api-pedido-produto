package br.com.fiap.techchallengeapipedidoproduto.core.config.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SwaggerRedirectControllerTest {

    private final SwaggerRedirectController controller = new SwaggerRedirectController();

    @Test
    public void deveRedirecionarParaSwagger() {
        // Teste direto do método
        String redirectUrl = controller.redirectToSwagger();
        assertEquals("redirect:/swagger-ui/index.html", redirectUrl, "O URL de redirecionamento deve apontar para a página do Swagger UI");
    }

    @Test
    public void deveRedirecionarParaSwaggerComMockMvc() throws Exception {
        // Teste com MockMvc
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }
}
