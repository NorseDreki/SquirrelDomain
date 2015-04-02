package com.squirrel.android.commands.similarity

import com.squirrel.android.commands.IObservableCommand
import rx.Observable
import com.squirrel.android.domain.repositories.IAuthRepository
import com.squirrel.android.domain.repositories.ISocialRepository
import com.squirrel.android.domain.model.SimilarityResult

/**
 * Calculates [SimilarityResult] for every friend of the current [User].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 27.01.2015.
 */
public open class FriendsSimilarityCommand(val authRepository: IAuthRepository,
                                           val socialRepository: ISocialRepository) :
        NaiveTracklistSimilarity(), IObservableCommand<SimilarityResult> {

    override fun observe(): Observable<SimilarityResult> {
        val user = authRepository.getCurrentUser()
        val userTracklist = socialRepository.getTracklist(user)
        val friendTracklists = socialRepository.getFriends(user)
                .flatMap({ socialRepository.getTracklist(it) })

        return similarities(userTracklist, friendTracklists)
    }
}
