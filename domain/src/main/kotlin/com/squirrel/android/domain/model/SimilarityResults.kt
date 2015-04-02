package com.squirrel.android.domain.model

import java.io.Serializable
import java.util.Collections

/**
 * Encapsulates list of [SimilarityResult]s.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 06.03.2015.
 */
public data class SimilarityResults(private val similarityResults: List<SimilarityResult>) : Serializable {

    fun asList(): List<SimilarityResult> {
        return Collections.unmodifiableList(similarityResults)
    }
}