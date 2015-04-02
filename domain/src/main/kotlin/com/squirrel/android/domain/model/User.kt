package com.squirrel.android.domain.model

import java.io.Serializable

/**
 * Represents user of application, also a member of social network(s).
 *
 * [User] is also a [Channel] because he can have [Tracklist] associated with him
 * and can be followed.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 11.01.2015.
 */
public class User(val firstName: String, val lastName: String):
        Channel(firstName + " " + lastName), Serializable
