package com.example.comicslibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.comicslibrary.comicsToString
import com.example.comicslibrary.model.CharacterResult
import com.example.comicslibrary.model.db.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class DBCharacter (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
){
    /*
    * companion object einai paromoio me to static sth java. H kotlin den exei static keyword
    * Sto companion object mporoume na exoume static fun kai properties. Den mporw na kalesw apo
    * alla arxeia omws me class name px DBCharacter.fromCharacter(). An thelw opwsdhpote na pateuxw ayth th
    * symperifora tou static sth java, mporw na valw ena @JvmStatic anotation panw apo to metos
    * poy tha kalesw th static leitoyrgia.
    *
    * Genika to companion object einai monadiko se kathe klassh. Sunithws to xrhsimopoioume gia
    * factory methods/fatory pattern.
    *
    * Factory pattern: Skopos aytou to design pattern, opws kai se ena pragmatiko ergostasio, einai h dhmioyrgia objects
    * Dld estw exw mia klassh Piece(kommati apo skakiera). Lamvanw ena typo se string ws paramtero. Mesa
    * sto companion object tha apofasisw se poia klassh anhkei ayto to piece analoga me th parametro(mporei na einai class
    * King, Queen etc) kai kalw th fun toy companion object kateytheian apo th klassh
    *
    * https://www.youtube.com/watch?v=1VWYP3S12Do
    * https://blog.logrocket.com/understanding-kotlin-design-patterns/
    * https://www.coursera.org/learn/design-patterns#modules
    *https://www.coursera.org/lecture/design-patterns/2-1-4-factory-method-pattern-LIUcy?utm_medium=sem&utm_source=gg&utm_campaign=b2c_emea_meta-back-end-developer_meta_ftcof_professional-certificates_arte_feb_24_dr_geo-multi_pmax_gads_lg-all&campaignid=21041939978&adgroupid=&device=c&keyword=&matchtype=&network=x&devicemodel=&adposition=&creativeid=&hide_mobile_promo&gad_source=2&gclid=Cj0KCQiA84CvBhCaARIsAMkAvkKJRefKp6s_3sH0GbrSMTCXsH1eem3ATAmRIL0XBCzmc3oBrWNpcxkaAt4EEALw_wcB
    * */
    companion object {
        fun fromCharacter(character: CharacterResult) = DBCharacter(
            id = 0,
            apiId = character.id,
            name = character.name,
            thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
            comics = character.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "no comics",
            description = character.description
        )
    }
}