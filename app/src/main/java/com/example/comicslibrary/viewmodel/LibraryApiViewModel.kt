package com.example.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicslibrary.model.api.MarvelApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* Gia na kanoume inject to prohg reposirory, kanoume
* anotate ayth th klassh me to @HiltViewModel kai
*to @Inject ston constructor
* */
@HiltViewModel
class LibraryApiViewModel @Inject constructor(private val repo: MarvelApiRepo): ViewModel() {
    val result = repo.characters
    val queryText = MutableStateFlow("")
    //https://kt.academy/article/cc-channel  -> polu kalo arthro!!xrhsimo link
    //ta Channels douleuoyn vohthitika me tis coroutines
    private val queryInput = Channel<String>(Channel.CONFLATED)

    init {
        retrieveCharacters()
    }

    /*
    https://developer.android.com/kotlin/coroutines
    https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/
    https://developer.android.com/topic/libraries/architecture/lifecycle

    * Coroutines to  viewModelScope.launch {  }, dld grafei asygxrono kwdika
    * kathe asyncronous operation runs se ena particular scope.
    *
    * Kathe viewmodel exei ena viewmodelscope. Ean to viewmodel ginei clear,
    * h coroutine tha ginei canceled. Dld h coroutine kanei oti xreiazetai mono
    * otan exoume active viewmodel.
    *
    * launch creates a new coroutine on the main thread kai ap oti katalava,
    *,einai dld coroutine builder
    * */
    //etsi kaloume to repo
    private fun retrieveCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { validateQuery(it) }
                //debounce shmainei oti den tha kanei result se query kathe character poy grafoume
                .debounce(1000)//perform to query ana 1 sec kai oxi kathe fora po kanei type charactira
                .collect() {
                    repo.query(it)
                }
        }
    }

   // auth tha kalestei apo to ui
    private fun validateQuery(query: String): Boolean = query.length >= 2

    fun onQueryUpdate(input: String) {
        queryText.value = input

        //auto kanei send to input sto channel,
        //to channel tha kanei receive ena flow,tha to kanei filter kai debounce
        //kai tha kanei invoke to repo query, to ui tha kanei connect me to result kai tha ananewthei
        queryInput.trySend(input)
    }

}