package br.com.fiap.techchallengeapipedidoproduto.core.config.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/")
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
