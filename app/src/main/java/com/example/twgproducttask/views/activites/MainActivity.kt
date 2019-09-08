package com.example.twgproducttask.views.activites

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.twgproducttask.repository.UserRepository
import com.example.twgproducttask.viewmodel.UserViewModel
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.widget.Toast
import com.example.twgproducttask.R
import com.example.twgproducttask.TWGApplication
import com.example.twgproducttask.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var  userViewModel: UserViewModel? = null
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TWGApplication.graph.inject(this)
        if(userRepository.getUserId() == null) {
            userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java!!)
            userViewModel!!.init()

            userViewModel!!.getUser()!!.observe(this, Observer<User> { user ->
                if (user != null) {
                    userRepository.storeUserId(user.UserID!!)

                } else {
                    Toast.makeText(this@MainActivity, "Get User failed!", Toast.LENGTH_SHORT).show()

                }

            })
        }

        bt_barCodeScan.setOnClickListener { VIEW->
            val intent: Intent = Intent(applicationContext, BarCodeScanActivity::class.java)
            startActivity(intent)
        }
        bt_productSearch.setOnClickListener { VIEW->
            val intent: Intent = Intent(applicationContext, ProductSearchActivity::class.java)
            startActivity(intent)
        }

    }


}
