package com.example.comicslibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.comicslibrary.model.db.Constants.CHARACTER_TABLE
import kotlinx.coroutines.flow.Flow

/*
* Polles fores pairnw data apo mia db, fetch apo service, authenticate a user.
*  kanw load data. To Flow einai enas typos pou kanei emit values sequentially.
* To paromoiazoun me solina sta px toy android. Einai synifasmena me coroutines kai
* alla data types, px vaseis. Yparxei to modelo producer(repo px h viewmodel me dedomena)
* -> solinas to Flow -> consumer(to view sunithws) gia ta values.
*Me tis coroutines to Flow stelnei dedomena kai asygxrona
*
* https://developer.android.com/kotlin/flow
*
*
* DAO- exei ta queries pou me ayta pairnoume apo th vash tis plhrofories poy theloume.
* Metatrepei to apotelesma to query se type gia thn efarmogh mas, dikh mas klasssh(px DBCharacter edw)
* */
@Dao
interface CharacterDao {
    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY id ASC")
    fun getCharacters(): Flow<List<DBCharacter>>

    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY id = :characterId")
    fun getCharacter(characterId: Int): Flow<DBCharacter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCharacter(character: DBCharacter)

    @Update
    fun updateCharacter(character: DBCharacter)

    @Delete
    fun deleteCharacter(character: DBCharacter)
}