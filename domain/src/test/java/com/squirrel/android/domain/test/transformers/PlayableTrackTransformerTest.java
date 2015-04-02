package com.squirrel.android.domain.test.transformers;

import com.squirrel.android.commands.decorators.PlayableTrackTransformer;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.repositories.IDatabase;
import com.squirrel.android.domain.repositories.IPlaybackRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class PlayableTrackTransformerTest {

    @Mock
    private IDatabase mockCachedTracks;

    @Mock
    private Track mockTrack;

    @Mock
    private IPlaybackRepository mockPlaybackRepository;

    @Mock
    private Track playableTrack;

    private PlayableTrackTransformer transformer;

    private Observable<Track> playableTrackObservable;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        transformer = new PlayableTrackTransformer(mockPlaybackRepository);
        playableTrackObservable = Observable.just(playableTrack);
    }

    @Test
    public void should_return_valid_playable_track() {
        given(mockPlaybackRepository.getPlayableTrack(mockTrack)).willReturn(playableTrackObservable);

        Track track = Observable.just(mockTrack).compose(transformer).toBlocking().first();

        assertThat(track, is(equalTo(playableTrack)));
    }
}
