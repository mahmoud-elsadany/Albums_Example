package com.cme_mahmoud.local.source


import com.cme_mahmoud.common.model.AlbumObject
import com.cme_mahmoud.data.repository.DatabaseDataSource
import com.cme_mahmoud.local.model.AlbumRealmObject
import io.realm.Realm
import java.util.UUID
import javax.inject.Inject


class DatabaseDataSourceImpl @Inject constructor(
) : DatabaseDataSource {

    override fun getAllAlbumsFromRealm(): List<AlbumObject> {


        val realm = Realm.getDefaultInstance()
        val albums = realm.where(AlbumRealmObject::class.java).findAll()
        val albumsLocal = realm.copyFromRealm(albums)


        val returnAlbums = ArrayList<AlbumObject>()
        albumsLocal.forEach { albumObject ->
            returnAlbums.add(
                AlbumObject(
                    name = albumObject.name,
                    artist = albumObject.artist,
                    image = albumObject.image,
                    genre = albumObject.genre,
                    releaseDate = albumObject.releaseDate,
                    iTunesLink = albumObject.iTunesLink,
                    copyright = albumObject.copyright
                )
            )
        }

        realm.close()

        println("Albums retrieved from realm: ${returnAlbums.size}")

        return returnAlbums
    }

    override fun deleteAllSavedAlbumsFromRealm() {
        val realm = Realm.getDefaultInstance()

        realm.executeTransactionAsync { realmOBj ->
            realmOBj.deleteAll()
        }
        realm.close()
    }

    override fun saveMultipleAlbumsToRealm(albums: List<AlbumObject>) {

        val realm = Realm.getDefaultInstance()

        println("Albums need to be saved: $albums")

        val albumRealmObjects = ArrayList<AlbumRealmObject>()
        albums.forEach { albumObject ->
            albumRealmObjects.add(
                AlbumRealmObject(
                    id = UUID.randomUUID().toString(),
                    name = albumObject.name,
                    artist = albumObject.artist,
                    image = albumObject.image,
                    genre = albumObject.genre,
                    releaseDate = albumObject.releaseDate,
                    iTunesLink = albumObject.iTunesLink,
                    copyright = albumObject.copyright
                )
            )
        }

        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(albumRealmObjects)
        }

        val albumsRealmResults = realm.where(AlbumRealmObject::class.java).findAll()
        println("Albums count in realm after save: ${albumsRealmResults.size}")

        realm.close()


    }

    override fun updateAllAlbumsInRealm(updatedAlbums: List<AlbumObject>) {

        val realm = Realm.getDefaultInstance()


        realm.executeTransactionAsync {
            val existingAlbums = it.where(AlbumRealmObject::class.java).findAll()
            existingAlbums.forEachIndexed { index, albumRealmObject ->
                val updatedAlbum =
                    updatedAlbums[index]
                albumRealmObject.name = updatedAlbum.name
                albumRealmObject.artist = updatedAlbum.artist
                albumRealmObject.image = updatedAlbum.image
                albumRealmObject.genre = updatedAlbum.genre
                albumRealmObject.releaseDate = updatedAlbum.releaseDate
                albumRealmObject.copyright = updatedAlbum.copyright
                albumRealmObject.iTunesLink = updatedAlbum.iTunesLink
            }
        }
        realm.close()
    }

    override fun hasCachedAlbums(): Boolean {

        val realm = Realm.getDefaultInstance()
        val albumsRealmResults = realm.where(AlbumRealmObject::class.java).findAll()

        println("Albums count in realm: ${albumsRealmResults.size}")
        return albumsRealmResults.size > 0


    }


}
