package com.squirrel.android.commands

import rx.Observable
import rx.schedulers.Schedulers
import rx.util.async.Async
import com.squirrel.android.domain.model.Tracklist
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.model.Channel
import com.squirrel.android.domain.repositories.IDatabase
import com.squirrel.android.domain.model.ArtistTrack
import com.squirrel.android.domain.model.PlayInfo
import com.squirrel.android.domain.ARTIST_TRACK_DELIMITER
import com.squirrel.android.domain.CACHED_TRACKLIST_TITLE
import com.squirrel.android.domain.CACHED_TRACK_KEY_PREFIX

/**
 * Encapsulates [Tracklist] of all locally cached [Track]s.
 *
 * @property cachedTracks database which records cached tracks and their playable
 *           local paths
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 13.03.2015.
 */
class CachedTracksCommand(val cachedTracks: IDatabase): IObservableCommand<Tracklist> {

    override fun observe(): Observable<Tracklist> {
        return Async.start({
                    val list = cachedTracks.findKeys(CACHED_TRACK_KEY_PREFIX)
                    list

                }, Schedulers.io())
                .flatMapIterable({
                    it
                })
                .map({
                    val t = Track(createArtistTrackFromCacheKey(it), Channel(CACHED_TRACKLIST_TITLE))
                    updateTrackPlayInfo(t)
                })
                .toList()
                .map({
                    Tracklist(it, Channel(CACHED_TRACKLIST_TITLE))
                })
    }

    private fun updateTrackPlayInfo(it: Track): Track {
        val query = CACHED_TRACK_KEY_PREFIX + ARTIST_TRACK_DELIMITER + it.getUniqueKey()
        val localPath = cachedTracks.get(query, javaClass<String>())
        it.playInfo = PlayInfo(url = localPath!!, isLocal = true)
        return it
    }

    private fun createArtistTrackFromCacheKey(key: String): ArtistTrack {
        val strings = key.split(ARTIST_TRACK_DELIMITER)
        return ArtistTrack(strings[1], strings[2])
    }
}
