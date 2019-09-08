package com.example.twgproducttask.repository

import android.arch.lifecycle.MutableLiveData
import com.example.twgproducttask.facades.SharedPreferencesFacade
import com.example.twgproducttask.model.User
import com.example.twgproducttask.network.interceptors.service.WarehouseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val preferencesFacade: SharedPreferencesFacade,
                                         private val wareHouseService: WarehouseService
) {

    // store user id in shared preference
    fun storeUserId(userId: String?) = preferencesFacade.saveUserId(userId!!)

    // get user id from shared perference
    fun getUserId() = preferencesFacade.getUserId()

    // API call to get user id
    fun getNewUserId(): MutableLiveData<User>? {
        var userData: MutableLiveData<User> = MutableLiveData<User>()
        wareHouseService.getNewUserId().enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
               // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                userData.value = null
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful == true) {
                    userData.value = response.body()
                }
            }

        })
        return userData
    }


}
