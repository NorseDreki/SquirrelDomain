package com.squirrel.android.domain.model

/**
 * Encapsulates relevant information for a playable [Track] needed
 * for and during playback.
 *
 * @property duration [Track] duration in seconds if this [Track] is playable
 *
 * @property url either a HTTP URL or local path to playable file if it was cached;
 *               null if no playable [Track] was resolved
 *
 * @property isLocal True for a playable [Track] cached locally, [url] is a local path;
 *                   false for [url] pointing to a HTTP resource;
 *                   null if no playable [Track] was resolved.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 30.03.2015.
 */
public data class PlayInfo(val duration: Int? = null,
                           val url: String,
                           val isLocal: Boolean)
