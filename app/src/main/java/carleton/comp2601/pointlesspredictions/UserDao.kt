package carleton.comp2601.pointlesspredictions

import androidx.room.*
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.entities.relations.UserWithPredictions

@Dao
interface UserDao {
    // users table methods
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE userName = :userName")
    fun findUserByUsername(userName: String): User

    @Query("SELECT * FROM users WHERE user_id = :user_id")
    fun findUserById(user_id: String?): User

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    // predictions table methods
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPrediction(prediction: Prediction)

    @Query("SELECT * FROM predictions")
    suspend fun getAllPredictions(): List<Prediction>

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :user_id")
    suspend fun getUserWithPredictions(user_id: Int): List<UserWithPredictions>
}