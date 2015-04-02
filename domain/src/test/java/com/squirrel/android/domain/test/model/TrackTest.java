package com.squirrel.android.domain.test.model;

import com.squirrel.android.domain.model.ArtistTrack;
import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.TrackIdentity;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackTest {

    private Track track;

    @Before
    public void setUp() {
        track = ObjectMother.createEmptyTrack();
    }

    @Test
    public void should_set_valid_tracklist_since_owner_when_parent_tracklist_is_null() {
        Tracklist tracklistSince = track.getTracklistSince();

        assertThat(tracklistSince.getOwner(), is(track.getOwner()));
    }

    @Test
    public void should_set_valid_tracklist_since_owner_when_parent_tracklist_not_null() {
        Tracklist tracklist = new Tracklist(Arrays.asList(track), new Channel("Channel"));
        track.setTracklist(tracklist);

        Tracklist tracklistSince = track.getTracklistSince();

        assertThat(tracklistSince.getOwner(), is(track.getTracklist().getOwner()));
    }

    @Test
    public void should_treat_tracks_equal_when_artisttracks_are_equal() {
        track.setTrackIdentity(new TrackIdentity(100, 200));

        Track anotherTrack = ObjectMother.createEmptyTrack();
        anotherTrack.setTrackIdentity(new TrackIdentity(300, 400));

        assertThat(track, is(equalTo(anotherTrack)));
    }

    @Test
    public void should_treat_tracks_different_when_artisttracks_are_different() {
        Track anotherTrack = new Track(new ArtistTrack("Another", "Track"), track.getOwner());

        assertThat(track, is(not(equalTo(anotherTrack))));
    }
}
