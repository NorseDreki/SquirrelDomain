package com.squirrel.android.domain.test.model;

import com.squirrel.android.domain.model.User;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTest {

    @Test
    public void should_set_channel_title_composed_from_full_name() {
        User user = new User("Andy", "Smith");

        assertThat(user.getTitle(), is("Andy Smith"));
    }
}
