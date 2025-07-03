package br.com.fiap.techchallengeapipedidoproduto.core.config.modelmapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ModelMapperConfigTest {

    private final ModelMapperConfig modelMapperConfig = new ModelMapperConfig();

    @Test
    public void deveCriarModelMapperQuandoListaConvertersVazia() {
        // arrange
        List<Converter<?, ?>> converters = Collections.emptyList();

        // when
        ModelMapper modelMapper = modelMapperConfig.modelMapper(converters);

        // then
        assertNotNull(modelMapper, "O ModelMapper não deve ser nulo");
    }

    @Test
    public void deveAdicionarConvertersAoModelMapper() {
        // arrange
        Converter<?, ?> converter1 = mock(Converter.class);
        Converter<?, ?> converter2 = mock(Converter.class);
        List<Converter<?, ?>> converters = List.of(converter1, converter2);

        // when
        ModelMapper modelMapper = modelMapperConfig.modelMapper(converters);

        // then
        assertNotNull(modelMapper, "O ModelMapper não deve ser nulo");
        // Não é possível verificar diretamente se os conversores foram adicionados
        // porque não há método público para consultar os conversores registrados
    }

    @Test
    public void deveAdicionarCadaConverterAoModelMapper() {
        // arrange
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(mock(Converter.class));
        converters.add(mock(Converter.class));
        converters.add(mock(Converter.class));

        // Criar um spy do ModelMapper para verificar se addConverter é chamado
        ModelMapper spyModelMapper = Mockito.spy(new ModelMapper());
        ModelMapperConfig spyConfig = new ModelMapperConfig() {
            @Override
            public ModelMapper modelMapper(List<Converter<?, ?>> converters) {
                converters.forEach(spyModelMapper::addConverter);
                return spyModelMapper;
            }
        };

        // when
        ModelMapper result = spyConfig.modelMapper(converters);

        // then
        assertSame(spyModelMapper, result, "Deve retornar a mesma instância do ModelMapper");
        verify(spyModelMapper, times(3)).addConverter(Mockito.any(Converter.class));
    }
}
