package com.eit.vipo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eit.vipo.web.rest.TestUtil;

public class ImagePropertyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagePropertyDTO.class);
        ImagePropertyDTO imagePropertyDTO1 = new ImagePropertyDTO();
        imagePropertyDTO1.setId(1L);
        ImagePropertyDTO imagePropertyDTO2 = new ImagePropertyDTO();
        assertThat(imagePropertyDTO1).isNotEqualTo(imagePropertyDTO2);
        imagePropertyDTO2.setId(imagePropertyDTO1.getId());
        assertThat(imagePropertyDTO1).isEqualTo(imagePropertyDTO2);
        imagePropertyDTO2.setId(2L);
        assertThat(imagePropertyDTO1).isNotEqualTo(imagePropertyDTO2);
        imagePropertyDTO1.setId(null);
        assertThat(imagePropertyDTO1).isNotEqualTo(imagePropertyDTO2);
    }
}
