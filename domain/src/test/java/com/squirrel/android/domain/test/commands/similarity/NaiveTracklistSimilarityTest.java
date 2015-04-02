package com.squirrel.android.domain.test.commands.similarity;

import com.squirrel.android.commands.similarity.NaiveTracklistSimilarity;
import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NaiveTracklistSimilarityTest {

    private static final int SCORE_ALEXEY_DMITRIEV_IVAN_ZORIN = 2;

    private NaiveTracklistSimilarity similarityEngine;

    @Before
    public void setUp() {
        similarityEngine = new NaiveTracklistSimilarity();
    }

    @Test
    public void should_calculate_valid_similarity_between_two_tracklists() throws Exception {
        Observable<Tracklist> baseTracklist = ObjectMother.getTracklistObservable("alexey_dmitriev.json");
        Observable<Tracklist> otherTracklist = ObjectMother.getTracklistObservable("ivan_zorin.json");

        SimilarityResult result = similarityEngine.similarities(baseTracklist, otherTracklist).toBlocking().single();

        assertThat(result.getScore(), is(SCORE_ALEXEY_DMITRIEV_IVAN_ZORIN));
        assertThat(result.getTracks().size(), is(SCORE_ALEXEY_DMITRIEV_IVAN_ZORIN));
        assertThat(result.getOtherChannel().getTitle(), is("Ivan Zorin"));
    }

    @Test
    public void should_calculate_zero_similarity_if_one_tracklist_is_empty() throws Exception {
        Observable<Tracklist> baseTracklist = ObjectMother.getTracklistObservable("alexey_dmitriev.json");
        Observable<Tracklist> otherTracklist = Observable.just(ObjectMother.createEmptyTracklist());

        SimilarityResult result = similarityEngine.similarities(baseTracklist, otherTracklist).toBlocking().single();

        assertThat(result.getScore(), is(0));
        assertThat(result.getTracks().size(), is(0));
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_npe_if_tracklist_is_null() throws Exception {
        Observable<Tracklist> baseTracklist = ObjectMother.getTracklistObservable("alexey_dmitriev.json");
        Observable<Tracklist> otherTracklist = Observable.just((Tracklist)null);

        similarityEngine.similarities(baseTracklist, otherTracklist).toBlocking().single();
    }
}
