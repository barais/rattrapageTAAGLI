package com.eit.vipo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eit.vipo.web.rest.TestUtil;

public class VipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VipoDTO.class);
        VipoDTO vipoDTO1 = new VipoDTO();
        vipoDTO1.setId(1L);
        VipoDTO vipoDTO2 = new VipoDTO();
        assertThat(vipoDTO1).isNotEqualTo(vipoDTO2);
        vipoDTO2.setId(vipoDTO1.getId());
        assertThat(vipoDTO1).isEqualTo(vipoDTO2);
        vipoDTO2.setId(2L);
        assertThat(vipoDTO1).isNotEqualTo(vipoDTO2);
        vipoDTO1.setId(null);
        assertThat(vipoDTO1).isNotEqualTo(vipoDTO2);
    }
}
