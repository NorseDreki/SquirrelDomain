package com.squirrel.android.domain.model

import java.util.Collections

/**
 * List of [Tag]s which characterize genre of the referred [Track].
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 06.03.2015.
 */
public data class Taglist(val track: Track,
                          val tags: List<Tag> = Collections.emptyList())