package com.squirrel.android.commands.decorators

import rx.Observable.Transformer
import rx.Observable
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.repositories.IPlaybackRepository
import com.squirrel.android.domain.LOGD

/**
 * Transforms [Track] in a way that resolves its playable URL.
 *
 * Does not take in account cached tracks, only [IPlaybackRepository] is queried.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 10.03.2015.
 */
public class PlayableTrackTransformer(val playbackRepository: IPlaybackRepository) : Transformer<Track, Track> {

    override fun call(t: Observable<Track>): Observable<Track> {
        return t.flatMap({
            LOGD.d("PlayableTrackTransformer for %s", it)
            playbackRepository.getPlayableTrack(it)
        })
    }
}
