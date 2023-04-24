package carleton.comp2601.pointlesspredictions.data

import androidx.room.*
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.entities.Friendship
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPrediction(prediction: Prediction)

    @Query("SELECT * FROM predictions")
    suspend fun getAllPredictions(): List<Prediction>

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :user_id")
    suspend fun getUserWithPredictions(user_id: Int): UserWithPredictions

    // friends table methods
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addUserFriendCrossRef(crossRef: UserFriendCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFriendship(friendship: Friendship)

    @Delete
    suspend fun deleteFriendship(friendship: Friendship)

    @Transaction
    @Query("SELECT * FROM friendships WHERE user_id1 = :user_id")
    suspend fun getFriendsOfUser(user_id: Int): List<Friendship>
}