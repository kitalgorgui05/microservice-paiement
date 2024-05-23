package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ReglementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReglementDTO.class);
        ReglementDTO reglementDTO1 = new ReglementDTO();
        reglementDTO1.setId(1L);
        ReglementDTO reglementDTO2 = new ReglementDTO();
        assertThat(reglementDTO1).isNotEqualTo(reglementDTO2);
        reglementDTO2.setId(reglementDTO1.getId());
        assertThat(reglementDTO1).isEqualTo(reglementDTO2);
        reglementDTO2.setId(2L);
        assertThat(reglementDTO1).isNotEqualTo(reglementDTO2);
        reglementDTO1.setId(null);
        assertThat(reglementDTO1).isNotEqualTo(reglementDTO2);
    }
}
