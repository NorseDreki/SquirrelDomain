package com.squirrel.android.domain.model

import java.util.Collections

/**
 * Encapsulates result of similarity computation between two [Channel]s.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 11.01.2015.
 */
public class SimilarityResult(val baseChannel: Channel,
                              val otherChannel: Channel,
                                  commonTracks: List<Track>,
                              val score: Int): Comparable<SimilarityResult> {

    val tracks: List<Track>

    {
        tracks = Collections.unmodifiableList(commonTracks)
    }

    override fun toString(): String {
        var result = "SimilarityResult between $baseChannel and $otherChannel "
            "with score $score and following commonTracks:"

        result += "\r\n"
        result += tracks.toString()
        return result
    }

    override fun compareTo(similarityResult: SimilarityResult): Int {
        return similarityResult.score - this.score
    }
}
