package com.example.comicslibrary.model

/*
* Data classes
* https://medium.com/@humzakhalid94/exploring-data-classes-in-kotlin-simplify-your-code-with-ease-fc52dea3e81a
*
* Polu kalo arthro sto medium. Sthn ousia oi data classes sufkritika me tis aples,
* dinoun kapoia pragmata etoima xwris na xreiazetai na grapsoume extra kodika p tha grafame kanonika.
* Ta poio shmantika einai to equality(==) metakty antikeimenwn, toString se antikeimena, hash code antikeimenwn(taytothta tous),
* copy antikeimena kanontas modify kapoia values tous an thelw na dhmioyrghsw ena neo(xrhsimo se
* objects me polles parametrous an thelw na allazw liges kathe fora)
* */

data class CharactersApiResponse(
    val code: String?,
    val status: String?,
    val attributionText: String?,
    val data: CharactersData?
)

data class CharactersData(
    val total: Int?,
    val results: List<CharacterResult>?
)

data class CharacterResult(
    val id: Int?,
    val name: String?,
    val description: String?,
    val resourceURI: String?,
    val urls: List<CharacterResultUrl>?,
    val thumbnail: CharacterThumbnail?,
    val comics: CharacterComics?
)

data class CharacterResultUrl(
    val type: String?,
    val url: String?
)

data class CharacterThumbnail(
    val path: String?,
    val extension: String?
)

data class CharacterComics(
    val items: List<CharacterComicsItems>?
)

data class CharacterComicsItems(
    val resourceURI: String?,
    val name: String?
)
