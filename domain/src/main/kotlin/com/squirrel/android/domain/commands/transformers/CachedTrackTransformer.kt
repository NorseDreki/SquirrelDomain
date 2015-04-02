package com.squirrel.android.commands.decorators

import rx.Observable.Transformer
import rx.Observable
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.repositories.IDatabase
import com.squirrel.android.domain.model.PlayInfo

/**
 * Resolves local playable path for [Track].
 *
 * If a [Track] has ever been cached, it will be updated with a valid path
 * to local file after this transformer is applied.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 10.03.2015.
 */
public class CachedTrackTransformer(val cachedTracks: IDatabase): Transformer<Track, Track> {

    override fun call(t: Observable<Track>): Observable<Track> {
        return t.map({
            val localPath = cachedTracks.get(it.getUniqueKey(), javaClass<String>())
            if (localPath != null) {
                it.playInfo = PlayInfo(it.getDuration(), localPath, true)
            }
            it
        })
    }
}
