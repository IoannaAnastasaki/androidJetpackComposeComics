package com.example.comicslibrary.model.api

import androidx.compose.runtime.mutableStateOf
import com.example.comicslibrary.model.CharacterResult
import com.example.comicslibrary.model.CharactersApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepo(private val api: MarvelApi) {
    /*
    https://medium.com/@paritasampa95/stateflow-in-android-812e4d82cac5

    To StateFlow einai kommati ths Kotlin library kai ekproswpei ena state mesa sthn efarmogh
    Einai to antistoixo tvn LiveData pou eixame sth Java.

    * MutableStateFlow- kanw update to state
    * enw den mporw na to kanw update me to StateFlow
    *
    StateFlow vrs Livedata

    Thetika gia StteFlow:
    1. Einai integrated poly kala me tis coroutines ths kotlin
    2.douleuoyn kalytera me asygxrona data streams.Mporoume na xrhsimop pollous operatprs opws maps
    /filter kai na kanoume transformation/manipulation twn data eukola
    3. Pio eykolo setup kai oxi boiler code sto testing me Kotlin coroutines
    4. (!!!) LiveData einai lifecycle aware(pou htan kalo ayto), poy katharizontai aitomasta otan ta components toy
    ginontai destroyed. To StaeFlow den einai kai auto einai kalo otan exoume na ta kanoume observe
    se non-ui compoments h long-lived background tasks
    * */

    //tha to xrhsimopoihsoume gia to viewmodel
    val characters = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())
    val characterDetails = mutableStateOf<CharacterResult?>(null)

    //edw tha kalesoume to api
    fun query(query: String) {
        characters.value = NetworkResult.Loading()
        api.getCharacters(query)
            //auto to enqueque stelnei th klhsh kai edw erxetai h apanthsh
            //to callback einai retrofit, prosoxh edw
            //to onResponse kai onFailure einai toy Callback toy retrofit poy ginetai impement
            //to Callback dld einai ena interface poy exei thn onResponse kai onFailure
            .enqueue(object: Callback<CharactersApiResponse>{

                override fun onResponse(
                    call: Call<CharactersApiResponse>,
                    response: Response<CharactersApiResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            //to it einai to CharactersApiResponse
                            characters.value = NetworkResult.Success(it)
                        }
                    }
                    else {
                        characters.value = NetworkResult.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<CharactersApiResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                        characters.value = NetworkResult.Error(it)
                    }
                    t.printStackTrace()
                }
            })
    }

    fun getSingleCharacter(id: Int?) {
        //efoson to id den einai null(to antistoixo tou guard)
        id?.let {
            characterDetails.value = characters
                .value.data?.data?.results?.firstOrNull{character ->
                    character.id == id
                }
        }
    }

}