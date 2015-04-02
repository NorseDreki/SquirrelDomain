package com.squirrel.android.domain.model

import java.io.Serializable

/**
 * Represents entity which can have associated [Tracklist], can be followed,
 * and can have [SimilarityResult] if it was compared to a base [Tracklist].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 19.02.2015.
 */
public open class Channel(val title: String) : Serializable {

    public var id: Long? = null

    public var followed: Boolean? = null

    public var similarityResult: SimilarityResult? = null

    override fun toString(): String {
        return "Channel{" + "title='" + title + '\'' + '}'
    }
}
