package com.squirrel.android.commands.similarity

import rx.Observable
import com.google.common.collect.ImmutableMultiset
import com.google.common.collect.Multisets
import com.squirrel.android.domain.model.SimilarityResult
import com.squirrel.android.domain.model.Tracklist
import com.squirrel.android.domain.LOGD

/**
 * Calculates [SimilarityResult] for the two given sources of [Tracklist]s.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 11.01.2015.
 */
public open class NaiveTracklistSimilarity {

    fun similarities(baseTracklist: Observable<Tracklist>,
                               otherTracklist: Observable<Tracklist>): Observable<SimilarityResult> {

        return Observable.combineLatest(baseTracklist, otherTracklist)
            {(baseTracklist, otherTracklist) ->
                val bt = ImmutableMultiset.copyOf(baseTracklist.getTracks())
                val ot = ImmutableMultiset.copyOf(otherTracklist.getTracks())
                val intersection = Multisets.intersection(bt, ot)

                val result = SimilarityResult(
                        baseTracklist.owner,
                        otherTracklist.owner,
                        intersection.toList(),
                        intersection.size()
                )

                LOGD.d("Similarity for %s", result.otherChannel)

                result
            }
    }
}
