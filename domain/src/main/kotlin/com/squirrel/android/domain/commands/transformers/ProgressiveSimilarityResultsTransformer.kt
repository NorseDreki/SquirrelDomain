package com.squirrel.android.commands.decorators

import rx.Observable
import java.util.ArrayList
import java.util.Collections
import com.squirrel.android.domain.model.SimilarityResults
import com.squirrel.android.domain.model.SimilarityResult
import rx.Observable.Transformer

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 06.03.2015.
 */
public class ProgressiveSimilarityResultsTransformer(): Transformer<SimilarityResult, SimilarityResults> {

    public val PORTION_SIZE: Int = 5;

    override fun call(t: Observable<SimilarityResult>): Observable<SimilarityResults> {
        return t.scan(SimilarityResults(ArrayList()), { similarityResults, similarityResult ->
                    val list = ArrayList(similarityResults.asList())
                    list.add(similarityResult)
                    Collections.sort(list)
                    SimilarityResults(list)
                })
                .filter({ (it.asList().size() % PORTION_SIZE) == 0})
    }
}
