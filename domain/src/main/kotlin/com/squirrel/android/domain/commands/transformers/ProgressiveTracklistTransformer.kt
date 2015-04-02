package com.squirrel.android.commands.decorators

import rx.Observable
import rx.Observable.Transformer
import java.util.ArrayList
import com.squirrel.android.domain.model.Track
import com.squirrel.android.domain.model.Tracklist
import com.squirrel.android.domain.model.Channel

/**
 * Returns series of [Tracklist]s by accumulating and adding every next [.PORTION_SIZE]
 * [Track]s.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 10.03.2015.
 */
public class ProgressiveTracklistTransformer(val tracklistTitle: String): Transformer<Track, Tracklist> {

    public val PORTION_SIZE: Int = 5;

    override fun call(t: Observable<Track>): Observable<Tracklist> {
        return t.scan(Tracklist(ArrayList(), Channel(tracklistTitle)), {tracklist, track ->
                    val tracks = ArrayList(tracklist.getTracks())
                    tracks.add(track)
                    tracklist.copy(tracks)
                })
                .filter({ (it.getTracks().size() % PORTION_SIZE) == 0})
    }
}
