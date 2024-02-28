package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDBRepoImpl(private val characterDao: CharacterDao): CollectionDBRepo {
    override suspend fun getCharacterFromRepo(): Flow<List<DBCharacter>> = characterDao.getCharacters()

    override suspend fun getCharacterfromRepo(characterId: Int): Flow<DBCharacter> = characterDao.getCharacter(characterId)
    override suspend fun addCharacterToRepo(character: DBCharacter) = characterDao.addCharacter(character)

    override suspend fun updateCharacterToRepo(character: DBCharacter) = characterDao.updateCharacter(character)

    override suspend fun deleteCharacterToRepo(character: DBCharacter) = characterDao.deleteCharacter(character)
}