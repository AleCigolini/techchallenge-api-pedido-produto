package br.com.fiap.techchallengeapipedidoproduto.core.config.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SpringDocConfigTest {

    private final SpringDocConfig springDocConfig = new SpringDocConfig();

    @Test
    public void deveConfigurarOpenAPI() {
        // when
        OpenAPI openAPI = springDocConfig.openAPI();

        // then
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertNotNull(openAPI.getExternalDocs());
        assertNotNull(openAPI.getComponents());

        Info info = openAPI.getInfo();
        assertEquals("Fiap Tech Challenge", info.getTitle());
        assertEquals("v1", info.getVersion());
        assertEquals("REST API da Fiap Tech Challenge", info.getDescription());
        assertEquals("Apache 2.0", info.getLicense().getName());
        assertEquals("http://springdoc.com", info.getLicense().getUrl());

        assertEquals("Fiap Tech Challenge", openAPI.getExternalDocs().getDescription());
        assertEquals("https://techchallenge.com", openAPI.getExternalDocs().getUrl());

        Components components = openAPI.getComponents();
        assertNotNull(components.getSchemas());
        assertNotNull(components.getResponses());
        assertFalse(components.getSchemas().isEmpty());
        assertFalse(components.getResponses().isEmpty());

        Map<String, ApiResponse> responses = components.getResponses();
        assertTrue(responses.containsKey("BadRequestResponse"));
        assertTrue(responses.containsKey("NotFoundResponse"));
        assertTrue(responses.containsKey("NotAcceptableResponse"));
        assertTrue(responses.containsKey("InternalServerErrorResponse"));

        ApiResponse badRequestResponse = responses.get("BadRequestResponse");
        assertEquals("Requisição inválida", badRequestResponse.getDescription());
        assertNotNull(badRequestResponse.getContent());

        ApiResponse notFoundResponse = responses.get("NotFoundResponse");
        assertEquals("Recurso não encontrado", notFoundResponse.getDescription());
        assertNotNull(notFoundResponse.getContent());

        ApiResponse notAcceptableResponse = responses.get("NotAcceptableResponse");
        assertEquals("Recurso não possui representação que poderia ser aceita pelo consumidor", notAcceptableResponse.getDescription());
        assertNotNull(notAcceptableResponse.getContent());

        ApiResponse internalServerErrorResponse = responses.get("InternalServerErrorResponse");
        assertEquals("Erro interno no servidor", internalServerErrorResponse.getDescription());
        assertNotNull(internalServerErrorResponse.getContent());
    }

    @Test
    public void deveConfigurarOpenApiCustomizer() {
        // when
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();

        // then
        assertNotNull(customizer);

        // Testar o comportamento do customizer com um OpenAPI simulado
        OpenAPI openAPI = new OpenAPI();
        io.swagger.v3.oas.models.Paths paths = new io.swagger.v3.oas.models.Paths();
        io.swagger.v3.oas.models.PathItem pathItem = new io.swagger.v3.oas.models.PathItem();

        // Simular uma operação GET
        io.swagger.v3.oas.models.Operation getOperation = new io.swagger.v3.oas.models.Operation();
        getOperation.setResponses(new ApiResponses());
        pathItem.setGet(getOperation);

        // Simular uma operação POST
        io.swagger.v3.oas.models.Operation postOperation = new io.swagger.v3.oas.models.Operation();
        postOperation.setResponses(new ApiResponses());
        pathItem.setPost(postOperation);

        // Simular uma operação PUT
        io.swagger.v3.oas.models.Operation putOperation = new io.swagger.v3.oas.models.Operation();
        putOperation.setResponses(new ApiResponses());
        pathItem.setPut(putOperation);

        // Simular uma operação DELETE
        io.swagger.v3.oas.models.Operation deleteOperation = new io.swagger.v3.oas.models.Operation();
        deleteOperation.setResponses(new ApiResponses());
        pathItem.setDelete(deleteOperation);

        // Simular uma operação PATCH (cai no caso default)
        io.swagger.v3.oas.models.Operation patchOperation = new io.swagger.v3.oas.models.Operation();
        patchOperation.setResponses(new ApiResponses());
        pathItem.setPatch(patchOperation);

        paths.addPathItem("/test", pathItem);
        openAPI.setPaths(paths);

        // Aplicar o customizer
        customizer.customise(openAPI);

        // Verificar se as respostas foram adicionadas corretamente
        ApiResponses getResponses = pathItem.getGet().getResponses();
        assertTrue(getResponses.containsKey("406"));
        assertTrue(getResponses.containsKey("500"));

        ApiResponses postResponses = pathItem.getPost().getResponses();
        assertTrue(postResponses.containsKey("400"));
        assertTrue(postResponses.containsKey("500"));

        ApiResponses putResponses = pathItem.getPut().getResponses();
        assertTrue(putResponses.containsKey("400"));
        assertTrue(putResponses.containsKey("500"));

        ApiResponses deleteResponses = pathItem.getDelete().getResponses();
        assertTrue(deleteResponses.containsKey("500"));

        // Verificar o caso default (PATCH)
        ApiResponses patchResponses = pathItem.getPatch().getResponses();
        assertTrue(patchResponses.containsKey("500"));
        assertEquals(1, patchResponses.size(), "O caso default deve adicionar apenas a resposta 500");
    }
}
