package com.squirrel.android.domain.test.commands;

import com.squirrel.android.commands.ChannelTracklistCommand;
import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.repositories.IDatabase;
import com.squirrel.android.domain.repositories.IMetadataRepository;
import com.squirrel.android.domain.repositories.ISocialRepository;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ChannelTracklistCommandTest {

    private static final int SIMILAR_TRACKS_SIZE = 5;

    private static final String CACHED_TRACK_PATH = "/storage0/some.mp3";

    @Mock
    IMetadataRepository mockMetadataRepository;

    @Mock
    ISocialRepository mockSocialRepository;

    @Mock
    IDatabase mockCachedTracks;

    @Mock
    Channel mockChannel;

    @Mock
    Track mockTrack;

    private List<Track> tracks;

    private ChannelTracklistCommand command;

    private Observable<Tracklist> tracklistObservable;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tracks = Collections.nCopies(
                SIMILAR_TRACKS_SIZE,
                ObjectMother.createEmptyTrack()
        );
        tracklistObservable = Observable.just(new Tracklist(tracks, mockChannel));

        command = new ChannelTracklistCommand(
                mockChannel,
                mockSocialRepository,
                mockMetadataRepository,
                mockCachedTracks
        );
    }

    @Test
    public void should_return_playlist_with_resolved_cached_tracks() {
        given(mockSocialRepository.getTracklist(mockChannel)).willReturn(tracklistObservable);
        given(mockCachedTracks.get(anyString(), eq(String.class)))
                .willReturn(CACHED_TRACK_PATH)
                .willReturn(CACHED_TRACK_PATH)
                .willReturn(null);

        Tracklist tracklist = command.observe().toBlocking().single();

        verify(mockSocialRepository).getTracklist(mockChannel);
        assertThat(tracklist.getTracks().size(), is(SIMILAR_TRACKS_SIZE));
        assertThat(tracklist.getTracks().get(0).getUrl(), is(CACHED_TRACK_PATH));
        assertThat(tracklist.getTracks().get(1).getUrl(), is(CACHED_TRACK_PATH));
    }

    @Test
    public void should_return_empty_tracklist_for_empty_channel() {
        given(mockSocialRepository.getTracklist(mockChannel)).willReturn(Observable.<Tracklist>empty());

        Tracklist tracklist = command.observe().toBlocking().single();

        verify(mockSocialRepository).getTracklist(mockChannel);
        assertThat(tracklist.getTracks().size(), is(0));
    }

    @Test
    public void should_return_empty_tracklist_for_channel_with_empty_tracklist() {
        given(mockSocialRepository.getTracklist(mockChannel))
                .willReturn(Observable.just(ObjectMother.createEmptyTracklist()));

        Tracklist tracklist = command.observe().toBlocking().single();

        verify(mockSocialRepository).getTracklist(mockChannel);
        assertThat(tracklist.getTracks().size(), is(0));
    }
}
