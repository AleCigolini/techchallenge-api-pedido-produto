package br.com.fiap.techchallengeapipedidoproduto.core.config.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/swagger")
    public String redirectToSwagger() {
        return "redirect:/pedidos-produtos/swagger-ui/index.html";
    }
}
