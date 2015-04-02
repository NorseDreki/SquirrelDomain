package com.squirrel.android.domain.test.transformers;

import com.squirrel.android.commands.decorators.ProgressiveSimilarityResultsTransformer;
import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.SimilarityResults;
import com.squirrel.android.domain.model.Track;
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
import static org.junit.Assert.assertTrue;

public class ProgressiveSimilarityResultsTransformerTest {

    @Mock
    private IDatabase mockCachedTracks;

    @Mock
    private Track mockTrack;

    @Mock
    private IPlaybackRepository mockPlaybackRepository;

    @Mock
    private Track playableTrack;


    private ProgressiveSimilarityResultsTransformer transformer;

    private List<SimilarityResult> similarityResults;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);

        transformer = new ProgressiveSimilarityResultsTransformer();

        similarityResults = Collections.nCopies(10, ObjectMother.createEmptySimilarityResult());
    }

    @Test
    public void should_return_tracklist_progressively() {
        List<SimilarityResults> results =
                Observable.from(similarityResults).compose(transformer).toList().toBlocking().single();

        for (int i = 0; i < results.size(); i++) {
            SimilarityResults result = results.get(i);
            assertThat(result.asList().size() % transformer.getPORTION_SIZE(), is(0));
            //TODO assert contains items from previous step
        }
    }

    @Test
    public void should_return_empty_results_for_empty_input() {
        List<SimilarityResults> results =
                Observable.<SimilarityResult>empty().compose(transformer).toList().toBlocking().single();

        assertThat(results.size(), is(1));
        assertThat(results.get(0).asList().size(), is(0));
    }

    @Test
    public void should_sort_results_asc() {
        List<SimilarityResults> results =
                Observable.from(similarityResults).compose(transformer).toList().toBlocking().single();

        for (int i = 0; i < results.size(); i++) {
            SimilarityResults result = results.get(i);
            assertTrue(ObjectMother.isSorted(result.asList()));
        }
    }
}
