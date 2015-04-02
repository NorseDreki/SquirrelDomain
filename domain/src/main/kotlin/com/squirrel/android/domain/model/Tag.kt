package com.squirrel.android.domain.model

/**
 * Encapsulates genre characterization for a particular [Track].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 27.01.2015.
 */
public data class Tag(val name: String,
                      val score: Int,
                      val artist: String,
                      val track: String)