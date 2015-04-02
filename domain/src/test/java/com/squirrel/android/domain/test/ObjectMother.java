package com.squirrel.android.domain.test;

import com.google.gson.Gson;
import com.squirrel.android.domain.model.ArtistTrack;
import com.squirrel.android.domain.model.Channel;
import com.squirrel.android.domain.model.Group;
import com.squirrel.android.domain.model.SimilarityResult;
import com.squirrel.android.domain.model.Track;
import com.squirrel.android.domain.model.Tracklist;
import com.squirrel.android.domain.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import rx.Observable;

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 22.03.2015.
 */
public class ObjectMother {

    public static Group createDefaultGroup() {
        return new Group("Group");
    }

    public static Tracklist createEmptyTracklist() {
        return new Tracklist(Collections.<Track>emptyList(), new Channel("Channel"));
    }

    public static User createEmptyUser() {
        return new User("Name", "Surname");
    }

    public static SimilarityResult createEmptySimilarityResult() {
        return new SimilarityResult(
                new Channel("Base Channel"),
                new Channel("Other Channel"),
                Collections.<Track>emptyList(),
                0
        );
    }

    public static Track createEmptyTrack() {
        return new Track(new ArtistTrack("Artist", "Track"), new Channel("Channel"));
    }

    /** {@code jsonFile} must contain at least 1 valid item */
    public static Observable<Tracklist> getTracklistObservable(String jsonFile) throws FileNotFoundException {
        List<Track> tracks = new ArrayList(Arrays.asList(parseJsonFile(jsonFile, Track[].class)));
        return Observable.just(new Tracklist(tracks, tracks.get(0).getOwner()));
    }

    public static Observable<Tracklist> getTracklistWithChannelObservable(String jsonFile, Channel channel)
            throws FileNotFoundException {

        List<Track> tracks = new ArrayList(Arrays.asList(parseJsonFile(jsonFile, Track[].class)));
        return Observable.just(new Tracklist(tracks, channel));
    }

    public static  <T> T parseJsonFile(String jsonFile, Class<T> clazz) throws FileNotFoundException {
        Gson gson = new Gson();

        File resourcesDirectory = new File("src/test/resources/");
        File resource = new File(resourcesDirectory, jsonFile);

        BufferedReader br = new BufferedReader(new FileReader(resource));
        T result = gson.fromJson(br, clazz);

        return result;
    }

    public static <T extends Comparable<? super T>> boolean isSorted(Iterable<T> iterable) {
        Iterator<T> iter = iterable.iterator();
        if (!iter.hasNext()) {
            return true;
        }
        T t = iter.next();
        while (iter.hasNext()) {
            T t2 = iter.next();
            if (t.compareTo(t2) > 0) {
                return false;
            }
            t = t2;
        }
        return true;
    }
}
