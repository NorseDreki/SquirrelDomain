package com.squirrel.android.domain.test.commands.similarity;

import com.squirrel.android.commands.similarity.GroupsSimilarityCommand;
import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.Group;
import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.User;
import com.squirrel.android.domain.repositories.IAuthRepository;
import com.squirrel.android.domain.repositories.ISocialRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

public class GroupsSimilarityCommandTest {

    private static final int NUM_GROUPS = 5;

    @Mock
    private IAuthRepository mockAuthRepository;

    @Mock
    private ISocialRepository mockSocialRepository;

    private GroupsSimilarityCommand command;

    private List<Group> groups;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        groups = Collections.nCopies(NUM_GROUPS, ObjectMother.createDefaultGroup());
        command = new GroupsSimilarityCommand(mockAuthRepository, mockSocialRepository);
    }

    @Test
    public void should_filter_out_groups_with_tracklist_size_below_threshold() throws FileNotFoundException {
        given(mockSocialRepository.getGroups(any(User.class))).willReturn(Observable.from(groups));
        given(mockSocialRepository.getTracklist(any(Channel.class)))
                .willReturn(ObjectMother.getTracklistObservable("alexey_dmitriev.json"))
                .willReturn(ObjectMother.getTracklistObservable("group_tracklist_size_20.json"));

        List<SimilarityResult> results = command.observe().toList().toBlocking().single();

        assertThat(results.size(), is(0));
    }
}
