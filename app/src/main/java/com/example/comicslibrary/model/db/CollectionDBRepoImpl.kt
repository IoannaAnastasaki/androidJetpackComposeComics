package com.example.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

/*
* Suspend functions einai apla synarthseis pou mporoun na ginoun start kai pause
* xwris na mplokaroun to kwdika. Einai sunifasmenes me tis coroutines.
* To Flow(poy eidame sto DAO) xrhsimopoiei tis suspend functions gia na kanei produce kai
* consume values asygxrona. Dld to Flow mporei na kanei px ena network request kai
* na dwsei to epomeno value xwris na mplokarei to main thread
* */
class CollectionDBRepoImpl(private val characterDao: CharacterDao): CollectionDBRepo {
    override suspend fun getCharacterFromRepo(): Flow<List<DBCharacter>> = characterDao.getCharacters()

    override suspend fun getCharacterfromRepo(characterId: Int): Flow<DBCharacter> = characterDao.getCharacter(characterId)
    override suspend fun addCharacterToRepo(character: DBCharacter) = characterDao.addCharacter(character)

    override suspend fun updateCharacterToRepo(character: DBCharacter) = characterDao.updateCharacter(character)

    override suspend fun deleteCharacterToRepo(character: DBCharacter) = characterDao.deleteCharacter(character)
}