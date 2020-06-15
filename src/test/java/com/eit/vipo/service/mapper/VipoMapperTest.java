package com.eit.vipo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VipoMapperTest {

    private VipoMapper vipoMapper;

    @BeforeEach
    public void setUp() {
        vipoMapper = new VipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(vipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vipoMapper.fromId(null)).isNull();
    }
}
