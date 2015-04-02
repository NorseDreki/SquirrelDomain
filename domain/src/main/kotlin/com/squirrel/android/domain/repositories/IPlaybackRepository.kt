package com.squirrel.android.domain.repositories

import com.squirrel.android.domain.model.Track
import rx.Observable

/**
 * Interface to a repository which contains playable [Track]s.
 *
 * It means that querying this repository would result in supplying
 * URLs which can be then played by [IPlayer].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 27.01.2015.
 */
public trait IPlaybackRepository {

    /** given the track, retrieve a playable URL and attach it to the track */
    fun getPlayableTrack(seed: Track): Observable<Track>
}