package hu.bme.aut.movesy.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.movesy.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String) : LiveData<User>

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    suspend fun updateOrInsert(user: User) {
        ///TODO("Test if works: Livedata<User or User?>")
        getUser(user.id).value ?: return insertUser(user)
        updateUser(user)
    }

    @Query("DELETE FROM users WHERE id = :userID")
    suspend fun deleteUser(userID: String)

}