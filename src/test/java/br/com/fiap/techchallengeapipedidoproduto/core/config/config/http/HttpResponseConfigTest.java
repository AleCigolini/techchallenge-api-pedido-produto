package br.com.fiap.techchallengeapipedidoproduto.core.config.config.http;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class HttpResponseConfigTest {

    private final HttpResponseConfig httpResponseConfig = new HttpResponseConfig();

    @Test
    public void deveRetornarBufferedImageHttpMessageConverter() {
        // when
        HttpMessageConverter<BufferedImage> converter = httpResponseConfig.createImageHttpMessageConverter();

        // then
        assertNotNull(converter);
        assertInstanceOf(BufferedImageHttpMessageConverter.class, converter);
    }
}
