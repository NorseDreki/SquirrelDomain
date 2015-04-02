package com.squirrel.android.commands

import rx.Observable
import com.squirrel.android.commands.decorators.PlayableTrackTransformer
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.repositories.IPlaybackRepository
import com.squirrel.android.domain.repositories.IMetadataRepository
import com.squirrel.android.domain.LOGD

/**
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 10.02.2015.
 */
class TopTracksCommand(val priority: Int,
                       val trackObservable: Observable<Track>,
                       val playbackRepository: IPlaybackRepository,
                       val metadataRepository: IMetadataRepository): IObservableCommand<Track> {

    override fun observe(): Observable<Track> {
        return trackObservable
                .flatMap({
                    LOGD.d("TopTracksCommand.observe() %s", it)
                    metadataRepository.getTopTracks(it)
                })
                .compose(PlayableTrackTransformer(playbackRepository))
    }
}
