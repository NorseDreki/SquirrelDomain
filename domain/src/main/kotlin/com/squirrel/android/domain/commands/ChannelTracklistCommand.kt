package com.squirrel.android.commands

import rx.Observable
import com.squirrel.android.commands.decorators.CachedTrackTransformer
import com.squirrel.android.domain.model.Channel
import com.squirrel.android.domain.repositories.ISocialRepository
import com.squirrel.android.domain.repositories.IMetadataRepository
import com.squirrel.android.domain.repositories.IDatabase
import com.squirrel.android.domain.model.Tracklist

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 06.03.2015.
 */
class ChannelTracklistCommand(val channel: Channel,
                              val socialRepository: ISocialRepository,
                              val metadataRepository: IMetadataRepository,
                              val cachedTracks: IDatabase): IObservableCommand<Tracklist> {

    override fun observe(): Observable<Tracklist> {
        return socialRepository.getTracklist(channel)
                .flatMapIterable({ it.getTracks() })
                .compose(CachedTrackTransformer(cachedTracks))
                .toList()
                .map({ Tracklist(it, channel) })
    }
}
