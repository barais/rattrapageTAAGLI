package com.eit.vipo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VipoEntryMapperTest {

    private VipoEntryMapper vipoEntryMapper;

    @BeforeEach
    public void setUp() {
        vipoEntryMapper = new VipoEntryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(vipoEntryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vipoEntryMapper.fromId(null)).isNull();
    }
}
