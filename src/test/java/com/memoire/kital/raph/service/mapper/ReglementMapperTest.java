package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReglementMapperTest {

    private ReglementMapper reglementMapper;

    @BeforeEach
    public void setUp() {
        reglementMapper = new ReglementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reglementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reglementMapper.fromId(null)).isNull();
    }
}
