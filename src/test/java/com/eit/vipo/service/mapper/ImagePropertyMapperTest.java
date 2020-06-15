package com.eit.vipo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ImagePropertyMapperTest {

    private ImagePropertyMapper imagePropertyMapper;

    @BeforeEach
    public void setUp() {
        imagePropertyMapper = new ImagePropertyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(imagePropertyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(imagePropertyMapper.fromId(null)).isNull();
    }
}
