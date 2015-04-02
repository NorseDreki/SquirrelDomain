package com.squirrel.android.domain.model

import java.io.Serializable
import java.util.Collections

/**
 * Encapsulates list of [Track]s owned by a particular [Channel].
 *
 * This class is immutable.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 18.03.2015.
 */
public data class Tracklist(private val tracks: List<Track>,
                            val owner: Channel) : Serializable {

    fun getTracks(): List<Track> {
        for (track in tracks) {
            track.tracklist = this
        }

        return Collections.unmodifiableList(tracks)
    }

    override fun toString(): String {
        val result = StringBuilder()
        result.append(owner)
        result.append("\r\n")

        for (track in tracks) {
            result.append(track)
            result.append("\r\n")
        }

        return result.toString()
    }
}
