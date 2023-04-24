package carleton.comp2601.pointlesspredictions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import carleton.comp2601.pointlesspredictions.entities.Friendship
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.entities.Prediction

@Database(
    entities = [
        User::class,
        Prediction::class,
        Friendship::class
    ],
    version = 10
)

abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}