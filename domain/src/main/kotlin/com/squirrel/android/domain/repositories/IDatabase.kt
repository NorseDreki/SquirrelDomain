package com.squirrel.android.domain.repositories

/**
 * Encapsulates a key-value database.
 *
 * Created by Alexey Dmitriev <mr.alex.dmitriev@gmail.com> on 21.02.2015.
 */
public trait IDatabase {

    public fun <T> get(key: String, clazz: Class<T>): T?

    public fun <T> put(key: String, entry: T)

    public fun remove(key: String)

    public fun <T> getList(key: String, clazz: Class<T>): List<T>

    public fun findKeys(prefix: String): List<String>

}