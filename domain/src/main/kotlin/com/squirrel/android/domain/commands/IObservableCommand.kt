package com.squirrel.android.commands

import rx.Observable

/**
 * Encapsulates command which returns an [Observable] as a result of computation.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 27.01.2015.
 */
trait IObservableCommand<T> {

    fun observe(): Observable<T>
}
