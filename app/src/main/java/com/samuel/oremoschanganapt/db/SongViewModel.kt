package com.samuelsumbane.oremoschanganapt.db

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.oremoschanganapt.MyApp
//import com.samuel.oremoschanganapt.db.data.songData
import com.samuel.oremoschanganapt.db.data.songsData
import com.samuelsumbane.oremoschanganapt.db.data.prayData
//import com.samuelsumbane.oremoschanganapt.MyApp
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SongViewModel: ViewModel() {

        private val realm = MyApp.realm

        val songs = realm
            .query<Song>().asFlow().map { results -> results.list.toList() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

        init {
            viewModelScope.launch {
                try {
                    val existingPrays = realm.query<Song>().find()
                    if (existingPrays.isEmpty()) {
                        songsData.forEach {
                            addSong(it.number, it.group, it.title, it.subTitle, it.body, it.loved)
                        }
                    }
                } catch (e: Exception){
                    Log.d("PrayViewModel", "Error saving pray", e)
                }
            }
        }


    private fun getNextId(): Int {
            val lastNote = realm.query<Song>().sort("songId", Sort.DESCENDING).first().find()
            return (lastNote?.songId ?: 0) + 1
        }


        fun addSong(
            songnumber: String,
            songgroup: String,
            songtitle: String,
            songSubtitle: String,
            songbody: String,
            songloved: Boolean
        ) {
            viewModelScope.launch {
                realm.write {
                    val song = Song().apply {
                        songId = getNextId()
                        number = songnumber
                        group = songgroup
                        title = songtitle
                        subTitle = songSubtitle
                        body = songbody
                        loved = songloved
                    }
                    copyToRealm(song, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }

        fun updateSong(
            songid: Int,
            songloved: Boolean
        ){
            viewModelScope.launch {
                realm.write {
                    var song = this.query<Song>("songId == $0", songid).find().first()
                    song.let {
                        song.loved = songloved
//                        song.lov
                    }
                    copyToRealm(song, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }
}