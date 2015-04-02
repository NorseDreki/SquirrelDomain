package com.squirrel.android.domain.test.commands;

import com.squirrel.android.commands.CachedTracksCommand;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.repositories.IDatabase;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import kotlin.KotlinNullPointerException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CachedTracksCommandTest {

    private static final String TRACK_KEY = "cachedTrack##Artist##Track";

    private static final String CACHED_TRACK_PATH = "/storage0/artist-track.mp3";

    private static final Integer CACHED_TRACKS_SIZE = 5;

    private static final String KEY_PREFIX = "cachedTrack";

    @Mock
    private IDatabase mockCachedTracks;

    @Mock
    private Track mockTrack;

    private CachedTracksCommand command;

    private List<String> cachedTrackKeys;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        cachedTrackKeys = Collections.nCopies(CACHED_TRACKS_SIZE, TRACK_KEY);
        command = new CachedTracksCommand(mockCachedTracks);
    }

    @Test
    public void should_return_valid_tracklist_which_includes_all_cached_tracks() {
        given(mockCachedTracks.findKeys(KEY_PREFIX)).willReturn(cachedTrackKeys);
        given(mockCachedTracks.get(anyString(), eq(String.class))).willReturn(CACHED_TRACK_PATH);

        Tracklist tracklist = command.observe().toBlocking().single();

        verify(mockCachedTracks).findKeys(KEY_PREFIX);
        verify(mockCachedTracks, times(CACHED_TRACKS_SIZE)).get(anyString(), eq(String.class));
        assertThat(tracklist.getTracks().size(), is(CACHED_TRACKS_SIZE));
        //assertThat(tracklist.asList(), everyItem(Matchers.<Track>hasProperty("isLocal", equalTo(true))));
        assertThat(tracklist.getTracks(), everyItem(Matchers.<Track>hasProperty("url", equalTo(CACHED_TRACK_PATH))));
    }

    @Test
    public void should_return_empty_tracklist_for_no_cached_tracks() {
        given(mockCachedTracks.findKeys(KEY_PREFIX)).willReturn(Collections.<String>emptyList());

        Tracklist tracklist = command.observe().toBlocking().single();

        verify(mockCachedTracks).findKeys(KEY_PREFIX);
        verify(mockCachedTracks, never()).get(anyString(), eq(String.class));
        assertThat(tracklist.getTracks().size(), is(0));
    }

    @Test(expected = KotlinNullPointerException.class)
    public void should_throw_npe_for_existing_key_without_path() {
        given(mockCachedTracks.findKeys(KEY_PREFIX)).willReturn(cachedTrackKeys);
        given(mockCachedTracks.get(anyString(), eq(String.class))).willReturn(null);

        command.observe().toBlocking().single();
    }
}
