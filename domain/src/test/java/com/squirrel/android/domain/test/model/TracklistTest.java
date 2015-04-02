package com.squirrel.android.domain.test.model;

import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TracklistTest {

    private static final String CHANNEL_TITLE = "Tracklist";

    private static final int NUM_TRACKS = 5;

    private Tracklist tracklist;

    private List<Track> tracks;

    @Before
    public void setUp() {
        tracks = Collections.nCopies(NUM_TRACKS, ObjectMother.createEmptyTrack());
        tracklist = new Tracklist(tracks, new Channel(CHANNEL_TITLE));
    }

    @Test
    public void should_set_tracks_tracklist_to_himself() {
        for (Track t: tracklist.getTracks()) {
            assertThat(t.getTracklist(), is(tracklist));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_allow_tracklist_tracks_addition() {
        tracklist.getTracks().add(ObjectMother.createEmptyTrack());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_allow_tracklist_tracks_deletion() {
        tracklist.getTracks().remove(0);
    }
}
