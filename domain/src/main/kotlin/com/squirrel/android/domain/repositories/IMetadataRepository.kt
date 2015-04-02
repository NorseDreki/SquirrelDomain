package com.squirrel.android.domain.repositories

import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.model.Tag
import com.squirrel.android.domain.model.Taglist
import rx.Observable

/**
 * Interface to repository which contains metadata for [Track]s.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 27.01.2015.
 */
public trait IMetadataRepository {

    /** retrieves sorted top tags for the given track */
    fun getTopTags(track: Track): Observable<Taglist>

    /** retrieves tracks which are similar to the given */
    fun getSimilarTracks(track: Track): Observable<Track>

    /** retrieves top tracks for the artist of the given track */
    fun getTopTracks(track: Track): Observable<Track>

    /** retrieves tracks sorted by popularity for the given tag */
    fun getTracksForTag(tag: Tag): Observable<Track>

    /** retrieves hyped tracks for this week sorted by popularity */
    fun getHypedTracks(): Observable<Track>

    /** retrieves loved tracks for this week sorted by popularity */
    fun getLovedTracks(): Observable<Track>
}