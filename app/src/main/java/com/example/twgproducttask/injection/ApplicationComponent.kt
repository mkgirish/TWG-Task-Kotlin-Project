package com.example.twgproducttask.injection


import com.campgladiator.injection.AndroidModule
import com.campgladiator.injection.NetworkModule
import com.example.twgproducttask.views.activites.MainActivity
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.viewmodel.ProductDetailViewModel
import com.example.twgproducttask.viewmodel.ProductSearchViewModel
import com.example.twgproducttask.viewmodel.UserViewModel
import com.example.twgproducttask.views.activites.fragments.ProductDetailFragment
import com.example.twgproducttask.views.activites.fragments.ProductSearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface ApplicationComponent {

    // Application
    fun inject(target: TWGApplication)

    // Activity
    fun inject(target: MainActivity)

    // Fragment
    fun inject(target: ProductSearchFragment)

    fun inject(target: ProductDetailFragment)

    // ViewModel
    fun inject(target: UserViewModel)
    fun inject(target: ProductSearchViewModel)
    fun inject(target: ProductDetailViewModel)




}
