package com.squirrel.android.domain.test.model;

import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.SimilarityResults;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SimilarityResultTest {

    private SimilarityResults similarityResults;

    @Before
    public void setUp() {
        similarityResults = new SimilarityResults(new ArrayList<SimilarityResult>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_allow_similarityresults_addition() {
        similarityResults.asList().add(ObjectMother.createEmptySimilarityResult());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_allow_tracklist_tracks_deletion() {
        similarityResults.asList().remove(0);
    }
}
