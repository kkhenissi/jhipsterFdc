package com.kkhenissi.fdc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kkhenissi.fdc.web.rest.TestUtil;

public class FdcUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FdcUser.class);
        FdcUser fdcUser1 = new FdcUser();
        fdcUser1.setId(1L);
        FdcUser fdcUser2 = new FdcUser();
        fdcUser2.setId(fdcUser1.getId());
        assertThat(fdcUser1).isEqualTo(fdcUser2);
        fdcUser2.setId(2L);
        assertThat(fdcUser1).isNotEqualTo(fdcUser2);
        fdcUser1.setId(null);
        assertThat(fdcUser1).isNotEqualTo(fdcUser2);
    }
}
