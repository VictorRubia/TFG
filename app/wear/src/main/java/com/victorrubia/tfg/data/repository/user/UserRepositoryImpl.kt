package com.victorrubia.tfg.data.repository.user

import android.util.Log
import com.victorrubia.tfg.data.model.user.User
import com.victorrubia.tfg.data.repository.user.datasource.UserCacheDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserLocalDataSource
import com.victorrubia.tfg.data.repository.user.datasource.UserRemoteDataSource
import com.victorrubia.tfg.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userCacheDataSource: UserCacheDataSource,
) : UserRepository {

    override suspend fun requestUser(){
        requestUserToPhone()
    }

    override suspend fun getUser(): User? {
        return getUserFromCache()
    }

    override suspend fun saveUser(user: User) {
        saveUserToCache(user)
    }

    suspend fun getUserFromCache() : User?{
        var user : User? = null

        try {
            user = userCacheDataSource.getUserFromCache()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        if(user != null){
            return user
        }
        else{
            user = getUserFromDB()
            if(user != null) userCacheDataSource.saveUserToCache(user)
        }

        return user
    }

    suspend fun getUserFromDB() : User?{
        var user : User? = null

        try {
            user = userLocalDataSource.getUserFromDB()
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        if(user != null){
            return user
        }
        else{
            requestUserToPhone()
            return null
        }
    }

    suspend fun requestUserToPhone(){
        userRemoteDataSource.requestUser()
    }

    suspend fun saveUserToDB(user : User){
        try {
            userLocalDataSource.clearAll()
            userLocalDataSource.saveUserToDB(user)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
    }

    suspend fun saveUserToCache(user : User){
        try {
            userCacheDataSource.clearAll()
            userCacheDataSource.saveUserToCache(user)
        }
        catch (exception : Exception){
            Log.e("MyTag", exception.message.toString())
        }
        saveUserToDB(user)
    }
}