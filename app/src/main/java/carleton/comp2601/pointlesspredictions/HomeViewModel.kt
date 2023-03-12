package carleton.comp2601.pointlesspredictions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.navigation.NavHostController

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
        ViewModel() {

        val userList: LiveData<List<User>> = userRepository.allUsers

        val foundUser: LiveData<User> = userRepository.foundUser

        fun getAllUsers(){
                userRepository.getAllUsers()
        }

        fun addUser(user: User){
                userRepository.addUser(user)
                getAllUsers()
        }

        fun updateUser(user: User){
                userRepository.updateUser(user)
                getAllUsers()
        }

        fun addUserInDB(
                user: User,
                homeViewModel: HomeViewModel
        ){
                homeViewModel.addUser(user)
                //navController.popBackStack()
        }



        }