package com.kkhenissi.fdc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kkhenissi.fdc.web.rest.TestUtil;

public class UserFdcTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFdc.class);
        UserFdc userFdc1 = new UserFdc();
        userFdc1.setId(1L);
        UserFdc userFdc2 = new UserFdc();
        userFdc2.setId(userFdc1.getId());
        assertThat(userFdc1).isEqualTo(userFdc2);
        userFdc2.setId(2L);
        assertThat(userFdc1).isNotEqualTo(userFdc2);
        userFdc1.setId(null);
        assertThat(userFdc1).isNotEqualTo(userFdc2);
    }
}
