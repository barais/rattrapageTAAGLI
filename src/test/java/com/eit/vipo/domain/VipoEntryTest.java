package com.eit.vipo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.eit.vipo.web.rest.TestUtil;

public class VipoEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VipoEntry.class);
        VipoEntry vipoEntry1 = new VipoEntry();
        vipoEntry1.setId(1L);
        VipoEntry vipoEntry2 = new VipoEntry();
        vipoEntry2.setId(vipoEntry1.getId());
        assertThat(vipoEntry1).isEqualTo(vipoEntry2);
        vipoEntry2.setId(2L);
        assertThat(vipoEntry1).isNotEqualTo(vipoEntry2);
        vipoEntry1.setId(null);
        assertThat(vipoEntry1).isNotEqualTo(vipoEntry2);
    }
}
