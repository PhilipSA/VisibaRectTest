package com.example.visiba.visibarectest.Repositories.Abstractions
import io.paperdb.Paper

abstract class BaseRepository<T> {
    abstract var dbName: String

    open fun saveObject(saveItem: T, itemKey: String) {
        Paper.book(dbName).write(itemKey, saveItem)
    }

    open fun loadAllItems(): MutableList<T> {
        var objectList = mutableListOf<T>()
        Paper.book(dbName).allKeys.forEach {
            objectList.add(Paper.book(dbName).read(it.toString()))
        }
        return objectList
    }

    open fun loadItem(itemKey: String): T? {
        return Paper.book(dbName).read(itemKey)
    }
}
