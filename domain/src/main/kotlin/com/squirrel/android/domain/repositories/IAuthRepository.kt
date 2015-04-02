package com.squirrel.android.domain.repositories

import com.squirrel.android.domain.model.User

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 18.02.2015.
 */
public trait IAuthRepository {

    fun getCurrentUser(): User

    fun saveAuthToken(authToken: AuthToken)

    fun loadAuthToken(): AuthToken?

    fun startAuthProcess()

    fun finishAuthProcess()

    fun logout()
}