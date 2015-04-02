package com.squirrel.android.domain.test.transformers;

import com.squirrel.android.commands.decorators.CachedTrackTransformer;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.repositories.IDatabase;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class CachedTrackTransformerTest {

    private static final String CACHED_TRACK_PATH = "/storage0/track.mp3";

    @Mock
    private IDatabase mockCachedTracks;

    private Track track;

    private CachedTrackTransformer transformer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        transformer = new CachedTrackTransformer(mockCachedTracks);
        track = ObjectMother.createEmptyTrack();
    }

    @Test
    public void should_set_valid_local_path_for_cached_track() {
        given(mockCachedTracks.get(track.getUniqueKey(), String.class)).willReturn(CACHED_TRACK_PATH);

        Track t = Observable.just(track).compose(transformer).toBlocking().single();

        assertThat(t.getUrl(), is(CACHED_TRACK_PATH));
        assertThat(t.isLocal(), is(true));
        assertThat(t, is(sameInstance(track)));
    }

    @Test
    public void should_not_set_any_url_for_track_not_in_cache() {
        Track t = Observable.just(track).compose(transformer).toBlocking().single();

        assertThat(t.getDuration(), is((Integer)null));
        assertThat(t.getUrl(), is((String)null));
        assertThat(t.isLocal(), is((Boolean)null));
        assertThat(t, is(sameInstance(track)));
    }
}
