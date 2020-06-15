package com.eit.vipo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eit.vipo.web.rest.TestUtil;

public class ImagePropertyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageProperty.class);
        ImageProperty imageProperty1 = new ImageProperty();
        imageProperty1.setId(1L);
        ImageProperty imageProperty2 = new ImageProperty();
        imageProperty2.setId(imageProperty1.getId());
        assertThat(imageProperty1).isEqualTo(imageProperty2);
        imageProperty2.setId(2L);
        assertThat(imageProperty1).isNotEqualTo(imageProperty2);
        imageProperty1.setId(null);
        assertThat(imageProperty1).isNotEqualTo(imageProperty2);
    }
}
