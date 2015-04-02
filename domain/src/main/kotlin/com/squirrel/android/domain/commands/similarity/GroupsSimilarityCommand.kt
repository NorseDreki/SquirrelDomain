package com.squirrel.android.commands.similarity

import com.squirrel.android.commands.IObservableCommand
import rx.Observable
import com.squirrel.android.domain.repositories.IAuthRepository
import com.squirrel.android.domain.repositories.ISocialRepository
import com.squirrel.android.domain.model.SimilarityResult
import com.squirrel.android.domain.LOGD
import com.squirrel.android.domain.MIN_GROUP_TRACKS

/**
 * Calculates [SimilarityResult] for every [Group] of the current [User].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 03.03.2015.
 */
public open class GroupsSimilarityCommand(val authRepository: IAuthRepository,
                                          val socialRepository: ISocialRepository) :
        NaiveTracklistSimilarity(), IObservableCommand<SimilarityResult> {

    override fun observe(): Observable<SimilarityResult> {
        val user = authRepository.getCurrentUser()
        val userTracklist = socialRepository.getTracklist(user)
        val groupTracklists = socialRepository.getGroups(user)
                .flatMap({
                    socialRepository.getTracklist(it)
                })
                .filter { it.getTracks().size() > MIN_GROUP_TRACKS }

        return similarities(userTracklist, groupTracklists)
    }
}
