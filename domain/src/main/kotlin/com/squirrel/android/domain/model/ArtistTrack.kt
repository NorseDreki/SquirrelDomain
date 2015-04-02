package com.squirrel.android.domain.model

import com.squirrel.android.domain.ARTIST_TRACK_DELIMITER

/**
 * Represent [Track]'s identity.
 *
 * Two [Track]s are considered equal when they have equal artist and track names.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 28.03.2015.
 */
public data class ArtistTrack(val artist: String, val track: String) {

    public fun getKey(): String {
        return artist + ARTIST_TRACK_DELIMITER + track
    }

    //TODO implement functions which clean up artist and track from odd characters
}
