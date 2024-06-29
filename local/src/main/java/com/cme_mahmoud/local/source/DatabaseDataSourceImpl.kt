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
        val albumsRealmResults = realm.where(AlbumRealmObject::class.java).findAll()
        val albumsList = realm.copyFromRealm(albumsRealmResults)

        val albums = albumsList.map { albumRealmObject ->
            AlbumObject(
                name = albumRealmObject.name,
                artist = albumRealmObject.artist,
                image = albumRealmObject.image,
                genre = albumRealmObject.genre,
                releaseDate = albumRealmObject.releaseDate,
                copyright = albumRealmObject.copyright,
                iTunesLink = albumRealmObject.iTunesLink
            )
        }

        realm.close()
        return albums
    }

    override fun deleteAllSavedAlbumsFromRealm() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync { realm ->
            realm.deleteAll()
        }
        realm.close()
    }

    override fun saveMultipleAlbumsToRealm(albums: List<AlbumObject>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync { realm ->
            albums.forEach { albumObject ->
                val albumRealmObject =
                    realm.createObject(AlbumRealmObject::class.java, albumObject.name)
                albumRealmObject.id = UUID.randomUUID().toString()
                albumRealmObject.name = albumObject.name
                albumRealmObject.artist = albumObject.artist
                albumRealmObject.image = albumObject.image
                albumRealmObject.genre = albumObject.genre
                albumRealmObject.releaseDate = albumObject.releaseDate
                albumRealmObject.copyright = albumObject.copyright
                albumRealmObject.iTunesLink = albumObject.iTunesLink
            }
        }
        realm.close()
    }

    override fun updateAllAlbumsInRealm(updatedAlbums: List<AlbumObject>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync { realm ->
            val existingAlbums = realm.where(AlbumRealmObject::class.java).findAll()
            existingAlbums.forEachIndexed { index, albumRealmObject ->
                val updatedAlbum =
                    updatedAlbums[index] // Assuming updatedAlbums is aligned with existing albums
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
        val albumsCount = realm.where(AlbumRealmObject::class.java).count()
        realm.close()
        return albumsCount > 0
    }


}
