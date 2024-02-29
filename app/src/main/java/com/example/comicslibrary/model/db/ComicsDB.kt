package com.example.comicslibrary.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

//define th vash mas
@Database(entities = [DBCharacter::class], version = 1, exportSchema = false)
abstract class CollectionDB: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}