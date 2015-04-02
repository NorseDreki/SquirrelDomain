package com.squirrel.android.domain.model

/**
 * Encapsulates [Track]'s identity in [ISocialRepository]s.
 *
 * @property trackId id of this [Track] in a social repository
 * @property ownerId id of owner of this [Track] in a social repository
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 31.03.2015.
 */
public data class TrackIdentity(val trackId: Long,
                                val ownerId: Long)
