//package com.samuelsumbane.oremoschanganapt.db
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.samuel.oremoschanganapt.MyApp
////import com.samuel.oremoschanganapt.db.data.songData
//import com.samuel.oremoschanganapt.db.data.songsData
////import com.samuelsumbane.oremoschanganapt.MyApp
//import io.realm.kotlin.UpdatePolicy
//import io.realm.kotlin.ext.query
//import io.realm.kotlin.query.Sort
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//
//class SongViewModel: ViewModel() {
//
//        private val realm = MyApp.realm
//
//        val songs = realm
//            .query<Song>().asFlow().map { results -> results.list.toList() }
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//        init {
//            viewModelScope.launch {
//                try {
//                    val existingPrays = realm.query<Song>().find()
//                    if (existingPrays.isEmpty()) {
//                        songsData.forEach {
//                            addSong(it.number, it.group, it.title, it.subTitle, it.body)
//                        }
//                    }
//                } catch (e: Exception){
//                    Log.d("PrayViewModel", "Error saving pray", e)
//                }
//            }
//        }
//
//
//        private fun getNextId(): Int {
//            val lastNote = realm.query<Song>().sort("songId", Sort.DESCENDING).first().find()
//            return (lastNote?.songId ?: 0) + 1
//        }
//
//        fun addSong(
//            songnumber: String,
//            songgroup: String,
//            songtitle: String,
//            songSubtitle: String,
//            songbody: String,
//        ) {
//            viewModelScope.launch {
//                realm.write {
//                    val song = Song().apply {
//                        songId = getNextId()
//                        number = songnumber
//                        group = songgroup
//                        title = songtitle
//                        subTitle = songSubtitle
//                        body = songbody
//                        loved = false
//                    }
//                    copyToRealm(song, updatePolicy = UpdatePolicy.ALL)
//                }
//            }
//        }
//
//        fun setLovedSong(songId: Int, loved: Boolean) {
//            viewModelScope.launch {
//                realm.write {
//                    val song = this.query<Song>("songId == $0", songId).find().first()
//                    song.let {
//                        song.loved = loved
//                    }
//                    copyToRealm(song, updatePolicy = UpdatePolicy.ALL)
//                }
//            }
//        }
//
////        fun selectSong(songId: Int): Song{
////            var song = Song()
////
////            viewModelScope.launch {
////                song= realm.query<Song>("songId == $0", songId).find().first()
////            }
////            return song
////        }
//
//        fun getSongById(songId: Int): Song? {
//            return realm.query<Song>("songId == $0", songId).first().find()
//        }
//
//
////        fun selectSongs(
////
////        ){
////            val songList = mutableStateOf<List<Song>>(emptyList())
////
////            viewModelScope.launch {
////                val allSongs = realm.query<Song>().find()
////                songList.value = allSongs
////            }
//////            return songList
////        }
//}