package com.squirrel.android.domain.repositories

import com.squirrel.android.domain.model.User
import rx.Observable
import com.squirrel.android.domain.model.Channel
import com.squirrel.android.domain.model.Tracklist
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.model.Group
import com.squirrel.android.domain.model.Post

/**
 * Interface for interaction with a social repository. This is intended to be
 * a music-related and/or music-containing social network.
 *
 * It's assumed that [User] would have a list of his favourite [Track]s,
 * a list of friends and [Group]s he belongs to.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 11.01.2015.
 */
public trait ISocialRepository {

    /** retrieves friends of a user */
    fun getFriends(user: User): Observable<User>

    /** retrieves a *single* track list which is associated with channel */
    fun getTracklist(channel: Channel): Observable<Tracklist>

    /** adds the track to track list of current user */
    fun addTrack(track: Track): Observable<Boolean>

    /** deletes track from track list of current user */
    fun deleteTrack(track: Track): Observable<Boolean>

    /** shares track to the recipient on behalf of current user */
    fun shareTrack(recipient: User, track: Track): Observable<Boolean>

    /** retrieves groups which current user belongs to */
    fun getGroups(user: User): Observable<Group>

    /** retrieves posts which belong to the channel */
    fun getPosts(channel: Channel): Observable<Post>
}
