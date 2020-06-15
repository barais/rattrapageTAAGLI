package com.eit.vipo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eit.vipo.web.rest.TestUtil;

public class VipoEntryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VipoEntryDTO.class);
        VipoEntryDTO vipoEntryDTO1 = new VipoEntryDTO();
        vipoEntryDTO1.setId(1L);
        VipoEntryDTO vipoEntryDTO2 = new VipoEntryDTO();
        assertThat(vipoEntryDTO1).isNotEqualTo(vipoEntryDTO2);
        vipoEntryDTO2.setId(vipoEntryDTO1.getId());
        assertThat(vipoEntryDTO1).isEqualTo(vipoEntryDTO2);
        vipoEntryDTO2.setId(2L);
        assertThat(vipoEntryDTO1).isNotEqualTo(vipoEntryDTO2);
        vipoEntryDTO1.setId(null);
        assertThat(vipoEntryDTO1).isNotEqualTo(vipoEntryDTO2);
    }
}
