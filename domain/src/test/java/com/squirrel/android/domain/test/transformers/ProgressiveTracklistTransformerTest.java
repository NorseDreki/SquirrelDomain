package com.squirrel.android.domain.test.transformers;

import com.squirrel.android.commands.decorators.ProgressiveTracklistTransformer;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.repositories.IDatabase;
import com.squirrel.android.domain.repositories.IPlaybackRepository;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProgressiveTracklistTransformerTest {

    public static final String TRACKLIST_TITLE = "Tracklist Title";

    @Mock
    private IDatabase mockCachedTracks;

    @Mock
    private IPlaybackRepository mockPlaybackRepository;

    @Mock
    private Track mockTrack;

    @Mock
    private Track playableTrack;

    private ProgressiveTracklistTransformer transformer;

    private Observable<Track> tracksObservable;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);

        transformer = new ProgressiveTracklistTransformer(TRACKLIST_TITLE);
        List<Track> tracks = Collections.nCopies(30, ObjectMother.createEmptyTrack());
        tracksObservable = Observable.from(tracks);
    }

    @Test
    public void should_return_tracklist_progressively() {
        List<Tracklist> tracklists = tracksObservable.compose(transformer).toList().toBlocking().single();

        for (int i = 0; i < tracklists.size(); i++) {
            Tracklist tracklist = tracklists.get(i);
            assertThat(tracklist.getTracks().size() % transformer.getPORTION_SIZE(), is(0));
            //TODO assert contains items from previous step
        }
    }

    @Test
    public void should_return_empty_tracklist_for_empty_tracks() {
        List<Tracklist> tracklists =
                Observable.<Track>empty().compose(transformer).toList().toBlocking().single();

        assertThat(tracklists.size(), is(1));
        assertThat(tracklists.get(0).getTracks().size(), is(0));
    }

    @Test
    public void should_set_channel_title_for_tracklist() {
        List<Tracklist> tracklists = tracksObservable.compose(transformer).toList().toBlocking().single();

        assertThat(tracklists.get(tracklists.size()-1).getOwner().getTitle(), is(TRACKLIST_TITLE));
    }
}
