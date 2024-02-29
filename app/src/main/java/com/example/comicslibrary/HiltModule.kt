package com.example.comicslibrary

import android.content.Context
import androidx.room.Room
import com.example.comicslibrary.connectivity.ConnectivityMonitor
import com.example.comicslibrary.model.api.ApiService
import com.example.comicslibrary.model.api.MarvelApiRepo
import com.example.comicslibrary.model.db.CharacterDao
import com.example.comicslibrary.model.db.CollectionDB
import com.example.comicslibrary.model.db.CollectionDBRepo
import com.example.comicslibrary.model.db.CollectionDBRepoImpl
import com.example.comicslibrary.model.db.Constants.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


//auto shmainei oti to Hilt tha mpei sto ViewModel
@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    //auto mesw enos viewmodel tha kanei inject ena MarvelApiRepo
    //tha xrhsimopoihsoume to repo sto viewmodel xwris na to exoume kanei
    //instantiate there, opote den iparxei couplin metaksu toys
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDB::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDB) =
     collectionDb.characterDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao): CollectionDBRepo = CollectionDBRepoImpl(characterDao)

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectivityMonitor.getInstance(context)
}