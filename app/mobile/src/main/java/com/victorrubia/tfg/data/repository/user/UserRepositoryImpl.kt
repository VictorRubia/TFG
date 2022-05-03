package com.victorrubia.tfg.data.repository.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDatasource
import com.victorrubia.tfg.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val userRemoteDataSource : UserRemoteDatasource,
    private val userLocalDataSource : UserLocalDataSource,
    private val userCacheDataSource : UserCacheDataSource,
) : UserRepository {

    override suspend fun getUser(email : String, password : String): User? {
        return getUserFromCache(email, password)
    }

    override suspend fun removeLocalUser() {
        userCacheDataSource.clearAll()
        userLocalDataSource.clearAll()
    }

    override suspend fun requestPasswordReminder(email : String): Boolean {
        return requestPasswordReminderToAPI(email)
    }

    override suspend fun sendApiKeyToWear() {
        try{
            userCacheDataSource.getUserFromCache()?.apiKey?.let { userRemoteDataSource.sendApiKeyToWear(it) }
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }
    }

    override suspend fun isWearConnected(): Boolean? {
        return userRemoteDataSource.isWearConnected()
    }

    private suspend fun getUserFromAPI(email : String, password : String) : User?{
        var user : User? = null

        if(email.isNotEmpty()){
            try{
                val response = userRemoteDataSource.getUser(email, password)
                val body = response.body()
                if(body != null){
                    user = User(body.apiKey, body.userDetails)
                }
            }
            catch (exception : Exception){
                Log.i("MyTag", exception.message.toString())
            }
        }

        return user
    }

    private suspend fun getUserFromDB(email : String, password : String) : User?{
        var user : User? = null

        try{
            user = userLocalDataSource.getUserFromDB()
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(user != null){
            return user
        }
        else{
            user = getUserFromAPI(email, password)
            if(user != null) userLocalDataSource.saveUserToDB(user)
        }

        return user
    }

    private suspend fun getUserFromCache(email : String, password : String) : User?{
        var user : User? = null

        try{
            user = userCacheDataSource.getUserFromCache()
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(user != null){
            return user
        }
        else{
            user = getUserFromDB(email, password)
            if (user != null) userCacheDataSource.saveUserToCache(user)
        }

        return user
    }

    private suspend fun requestPasswordReminderToAPI(email : String) : Boolean{

        var confirmation : Response<*>? = null

        try{
            confirmation = userRemoteDataSource.rememberPassword(email)
            Log.i("MYTAG", "Request Password -> " + (confirmation.raw().code == 200).toString())
        }
        catch (exception : Exception){
            Log.i("MyTag", exception.message.toString())
        }

        if(confirmation != null){
            return confirmation.raw().code == 200
        }

        return false

    }

}