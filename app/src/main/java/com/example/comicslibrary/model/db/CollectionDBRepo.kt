package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDBRepo {
    suspend fun getCharacterFromRepo(): Flow<List<DBCharacter>>
    suspend fun getCharacterfromRepo(characterId: Int): Flow<DBCharacter>
    suspend fun addCharacterToRepo(character: DBCharacter)
    suspend fun updateCharacterToRepo(character: DBCharacter)
    suspend fun deleteCharacterToRepo(character: DBCharacter)
}