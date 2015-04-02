package com.squirrel.android.domain.test.commands;

import com.squirrel.android.commands.SimilarTracksCommand;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.repositories.IMetadataRepository;
import com.squirrel.android.domain.repositories.IPlaybackRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SimilarTracksCommandTest {

    private static final int SIMILAR_TRACKS_SIZE = 5;

    private static final int NUM_SEED_TRACKS = 3;

    @Mock
    private IMetadataRepository mockMetadataRepository;

    @Mock
    private IPlaybackRepository mockPlaybackRepository;

    @Mock
    private Track mockPlayableTrack;

    @Mock
    private Track mockTrack;

    private List<Track> similarTracks;

    private SimilarTracksCommand command;

    private Observable<Track> similarTracksObservble;

    private Observable<Track> playableTrackObservable;

    private Observable<Track> trackObservable;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        similarTracks = Collections.nCopies(SIMILAR_TRACKS_SIZE, mockTrack);
        similarTracksObservble = Observable.from(similarTracks);
        playableTrackObservable = Observable.just(mockTrack);
        trackObservable = Observable.just(mockTrack);
        command = new SimilarTracksCommand(10, trackObservable, mockPlaybackRepository, mockMetadataRepository);
    }

    @Test
    public void should_return_playable_similar_tracks_for_single_seed_track() {
        given(mockMetadataRepository.getSimilarTracks(any(Track.class))).willReturn(similarTracksObservble);
        given(mockPlaybackRepository.getPlayableTrack(any(Track.class))).willReturn(playableTrackObservable);

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository).getSimilarTracks(mockTrack);
        verify(mockPlaybackRepository, times(SIMILAR_TRACKS_SIZE)).getPlayableTrack(mockTrack);
        assertThat(tracks.size(), is(SIMILAR_TRACKS_SIZE));
    }

    @Test
    public void should_return_playable_similar_tracks_for_many_seed_tracks() {
        trackObservable = Observable.from(Arrays.asList(mockTrack, mockTrack, mockTrack));
        command = new SimilarTracksCommand(10, trackObservable, mockPlaybackRepository, mockMetadataRepository);
        given(mockMetadataRepository.getSimilarTracks(any(Track.class))).willReturn(similarTracksObservble);
        given(mockPlaybackRepository.getPlayableTrack(any(Track.class))).willReturn(playableTrackObservable);

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository, times(NUM_SEED_TRACKS)).getSimilarTracks(mockTrack);
        verify(mockPlaybackRepository, times(SIMILAR_TRACKS_SIZE * NUM_SEED_TRACKS)).getPlayableTrack(mockTrack);
        assertThat(tracks.size(), is(SIMILAR_TRACKS_SIZE * NUM_SEED_TRACKS));
    }

    @Test
    public void should_return_empty_tracks_when_no_seed_track_given() {
        command = new SimilarTracksCommand(10, Observable.<Track>empty(), mockPlaybackRepository, mockMetadataRepository);

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository, never()).getSimilarTracks(Matchers.<Track>anyObject());
        verify(mockPlaybackRepository, never()).getPlayableTrack(Matchers.<Track>anyObject());
        assertThat(tracks.size(), is(0));
    }

    @Test
    public void should_return_empty_tracks_when_no_similar_tracks_found() {
        given(mockMetadataRepository.getSimilarTracks(any(Track.class))).willReturn(Observable.<Track>empty());

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository).getSimilarTracks(mockTrack);
        verify(mockPlaybackRepository, never()).getPlayableTrack(Matchers.<Track>anyObject());
        assertThat(tracks.size(), is(0));
    }

    @Test
    public void should_return_empty_tracks_when_no_playable_tracks_found() {
        given(mockMetadataRepository.getSimilarTracks(any(Track.class))).willReturn(similarTracksObservble);
        given(mockPlaybackRepository.getPlayableTrack(any(Track.class))).willReturn(Observable.<Track>empty());

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository).getSimilarTracks(mockTrack);
        verify(mockPlaybackRepository, times(SIMILAR_TRACKS_SIZE)).getPlayableTrack(Matchers.<Track>anyObject());
        assertThat(tracks.size(), is(0));
    }

    @Test
    public void should_return_less_tracks_than_number_of_similar_when_not_all_playable_tracks_available() {
        given(mockMetadataRepository.getSimilarTracks(any(Track.class))).willReturn(similarTracksObservble);
        given(mockPlaybackRepository.getPlayableTrack(any(Track.class)))
                .willReturn(Observable.<Track>empty())
                .willReturn(Observable.<Track>empty())
                .willReturn(playableTrackObservable);

        List<Track> tracks = command.observe().toList().toBlocking().single();

        verify(mockMetadataRepository).getSimilarTracks(mockTrack);
        verify(mockPlaybackRepository, times(SIMILAR_TRACKS_SIZE)).getPlayableTrack(Matchers.<Track>anyObject());
        assertThat(tracks.size(), is(SIMILAR_TRACKS_SIZE - 2));
    }
}
