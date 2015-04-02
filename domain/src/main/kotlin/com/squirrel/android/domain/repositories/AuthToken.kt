package com.squirrel.android.domain.repositories

import com.squirrel.android.domain.model.User

/**
 * Encapsulates auth token assigned to [User] upon successful authentication
 * in a social network.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 18.02.2015.
 */
public class AuthToken {

    public var token: String? = null

    public var user: User? = null
}