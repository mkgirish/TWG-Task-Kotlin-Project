package com.example.twgproducttask.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.repository.UserRepository
import com.example.twgproducttask.model.User
import javax.inject.Inject


class UserViewModel: ViewModel() {

    private var mutableLiveData: MutableLiveData<User>? = null
    @Inject
    lateinit var userRepository: UserRepository

    fun init() {
        TWGApplication.graph.inject(this)

        if (mutableLiveData != null) {
            return
        }

        mutableLiveData = userRepository!!.getNewUserId()

    }

    fun getUser(): LiveData<User>? {
        return mutableLiveData
    }
}