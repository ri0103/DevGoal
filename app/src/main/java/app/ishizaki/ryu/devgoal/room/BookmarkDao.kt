package app.ishizaki.ryu.devgoal.room

import androidx.room.*
import app.ishizaki.ryu.devgoal.dataclass.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    fun getAll(): List<Bookmark>

    @Insert
    fun insertAll(vararg bookmark: Bookmark)

    @Insert
    fun insert(bookmark: Bookmark)

    @Update
    fun update(vararg bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark WHERE id = :id")
    fun getByBookmarkId(id: Int): Bookmark

    @Query("delete from bookmark")
    fun deleteAll()
}