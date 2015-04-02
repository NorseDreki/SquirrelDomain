package com.squirrel.android.domain.test.commands.similarity;

import com.squirrel.android.commands.similarity.FriendsSimilarityCommand;
import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.model.User;
import com.squirrel.android.domain.repositories.IAuthRepository;
import com.squirrel.android.domain.repositories.ISocialRepository;
import com.squirrel.android.domain.test.ObjectMother;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FriendsSimilarityCommandTest {

    private static final int NUM_FRIENDS = 4;

    @Mock
    private IAuthRepository mockAuthRepository;

    @Mock
    private ISocialRepository mockSocialRepository;

    private final List<Integer> SIMILARITY_SCORES = Arrays.asList(
            2, //Ivan Zorin
            2, //Andrey Dugin
            2, //Melany Higgies
            5  //Viktor Gusev
    );

    private FriendsSimilarityCommand command;

    private List<User> users;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        command = new FriendsSimilarityCommand(mockAuthRepository, mockSocialRepository);
        users = Collections.nCopies(NUM_FRIENDS, ObjectMother.createEmptyUser());
    }

    @Test
    public void should_calculate_valid_similarity_score_between_two_tracklists() throws Exception {
        given(mockSocialRepository.getFriends(any(User.class))).willReturn(Observable.from(users));
        given(mockSocialRepository.getTracklist(any(Channel.class)))
                .willReturn(ObjectMother.getTracklistObservable("alexey_dmitriev.json"))
                .willReturn(ObjectMother.getTracklistObservable("ivan_zorin.json"))
                .willReturn(ObjectMother.getTracklistObservable("andrey_dugin.json"))
                .willReturn(ObjectMother.getTracklistObservable("melany_higgies.json"))
                .willReturn(ObjectMother.getTracklistObservable("viktor_gusev.json"));

        Iterator<SimilarityResult> iterator = command.observe().toBlocking().getIterator();
        Iterator<Integer> scoresIterator = SIMILARITY_SCORES.iterator();

        verify(mockSocialRepository).getFriends(any(User.class));
        verify(mockSocialRepository, times(NUM_FRIENDS + 1)).getTracklist(any(Channel.class));
        while (iterator.hasNext()) {
            SimilarityResult result = iterator.next();
            Integer nextScore = scoresIterator.next();
            assertThat(result.getScore(), is(nextScore));
            assertThat(result.getTracks().size(), is(nextScore));
        }
    }

    @Test
    public void should_set_valid_channel_title_for_similarity_result() throws FileNotFoundException {
        Observable<Tracklist> ivanZorinTracklist =
                ObjectMother.getTracklistWithChannelObservable("ivan_zorin.json", new Channel("Ivan Zorin"));
        Observable<Tracklist> alexeyDmitrievTracklist =
                ObjectMother.getTracklistObservable("alexey_dmitriev.json");

        given(mockSocialRepository.getFriends(any(User.class))).willReturn(Observable.just(users.get(0)));
        given(mockSocialRepository.getTracklist(any(Channel.class)))
                .willReturn(alexeyDmitrievTracklist)
                .willReturn(ivanZorinTracklist);

        List<SimilarityResult> results = command.observe().toList().toBlocking().single();

        verify(mockSocialRepository).getFriends(any(User.class));
        verify(mockSocialRepository, times(2)).getTracklist(any(Channel.class));

        SimilarityResult result = results.get(0);
        assertThat(result.getOtherChannel().getTitle(), is("Ivan Zorin"));
    }

    @Test
    public void should_return_empty_results_for_empty_user_tracklist() {
        given(mockSocialRepository.getFriends(any(User.class))).willReturn(Observable.from(users));
        given(mockSocialRepository.getTracklist(any(Channel.class))).willReturn(Observable.<Tracklist>empty());

        List<SimilarityResult> results = command.observe().toList().toBlocking().single();

        assertThat(results.size(), is(0));
    }

    @Test
    public void should_return_empty_results_for_empty_friends() {
        given(mockSocialRepository.getFriends(any(User.class))).willReturn(Observable.<User>empty());
        given(mockSocialRepository.getTracklist(any(Channel.class))).willReturn(Observable.<Tracklist>empty());

        List<SimilarityResult> results = command.observe().toList().toBlocking().single();

        assertThat(results.size(), is(0));
    }
}
