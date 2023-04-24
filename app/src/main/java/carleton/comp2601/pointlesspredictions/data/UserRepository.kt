package carleton.comp2601.pointlesspredictions.data

import androidx.lifecycle.MutableLiveData
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User
import kotlinx.coroutines.*

class UserRepository(private val userDao: UserDao) {
    val allUsers = MutableLiveData<List<User>>()
    val foundUser = MutableLiveData<User>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addUser(newUser: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(newUser)
        }
    }

    fun updateUser(newUser: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateUser(newUser)
        }
    }

    fun deleteUser(user: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.deleteUser(user)
        }
    }

    fun findUserByUsername(userName: String) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.findUserByUsername(userName)
        }

    }

    fun getAllUsers() {
        val result = coroutineScope.async(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }

    fun addPrediction(prediction: Prediction) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.upsertPrediction(prediction)
        }
    }


}