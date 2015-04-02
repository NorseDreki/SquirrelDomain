package com.squirrel.android.domain.model

import java.io.Serializable
import java.util.Arrays

/**
 * Represents a musical track composed by an artist.
 *
 * @param artistTrack holds artist name and track title
 * @param owner origin of this [Track], might be user's [Tracklist], result of a search,
 *              result of an artist's "top tracks" operation, etc.
 *
 * Created by upelsin.
 */
public open class Track(private val artistTrack: ArtistTrack,
                        public val owner: Channel) : Serializable {

    /**
     * [Tracklist] to which this [Track] belongs to.
     * Owning [Channel] of this [Tracklist] may differ from this
     * [Track]'s [.owner].
     */
    transient public var tracklist: Tracklist? = null

    /** list of tags which are applied to this [Track] */
    transient public var taglist: Taglist? = null

    /** information needed for and during playback */
    internal var playInfo: PlayInfo? = null;

    /** identity in social repositories */
    internal var trackIdentity: TrackIdentity? = null

    /**
     * Returns a [Tracklist] which is a sublist of this [Track]'s owning [Tracklist]
     * which starts from this [Track] and spans to the end of owning [Tracklist].
     *
     * If owning [Tracklist] is unknown, the returned [Tracklist] consists of this
     * [Track] only.
     */
    public fun getTracklistSince(): Tracklist {
        if (tracklist != null) {
            val tracks = tracklist!!.getTracks()
            val tracksSince = tracks.subList(tracks.indexOf(this), tracks.size())
            return Tracklist(tracksSince, tracklist!!.owner)
        }

        return Tracklist(Arrays.asList(this), owner)
    }

    /**
     * Returns a human-readable key which uniquely represents this [Track].
     */
    public open fun getUniqueKey(): String {
        return artistTrack.getKey()
    }

    public fun getArtist(): String {
        return artistTrack.artist
    }

    public fun getTrack(): String {
        return artistTrack.track
    }

    public fun getDuration(): Int? {
        return playInfo?.duration
    }

    public open fun getUrl(): String? {
        return playInfo?.url
    }

    public fun isLocal(): Boolean? {
        return playInfo?.isLocal
    }

    public fun getTrackId(): Long? {
        return trackIdentity?.trackId
    }

    public fun getOwnerId(): Long? {
        return trackIdentity?.ownerId
    }

    // [Track] is identified only by its artistTrack
    override fun equals(o: Any?): Boolean {
        return artistTrack.equals((o as Track).artistTrack)
    }

    override fun hashCode(): Int {
        return artistTrack.hashCode()
    }

    override fun toString(): String {
        return artistTrack.toString()
    }
}
