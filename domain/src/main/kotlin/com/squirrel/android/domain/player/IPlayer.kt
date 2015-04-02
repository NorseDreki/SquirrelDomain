package com.squirrel.android.domain.player

import com.squirrel.android.domain.model.Track

/**
 * Interface for track playback.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 10.02.2015.
 */
public trait IPlayer {

    fun play(track: Track)

    fun stop()
}
