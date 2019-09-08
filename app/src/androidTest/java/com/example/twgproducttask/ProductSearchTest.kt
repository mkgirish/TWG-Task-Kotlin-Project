package com.example.twgproducttask

import android.content.Intent
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.twgproducttask.views.activites.ProductSearchActivity
import com.example.twgproducttask.views.activites.fragments.ProductDetailFragment
import com.example.twgproducttask.views.activites.fragments.ProductSearchFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProductSearchTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ProductSearchActivity::class.java, false, false)

       @Test
    fun loadProductSearchTest() {

        mActivityTestRule.launchActivity(null)
        mActivityTestRule.activity.runOnUiThread {
            val fragment = ProductSearchFragment.newInstance()
            mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.root_layout, fragment).commitNow()

        }
        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.search_view))
            .perform(SearchViewActionExtension.submitText("tv"))


           // Make sure Espresso does not time out
           IdlingPolicies.setMasterPolicyTimeout(10000 * 2, TimeUnit.MILLISECONDS);
           IdlingPolicies.setIdlingResourceTimeout(10000 * 2, TimeUnit.MILLISECONDS);
           // Now we wait
           val idlingResource : IdlingResource =  ElapsedTimeIdlingResource(10000);
           Espresso.registerIdlingResources(idlingResource)

           onView(ViewMatchers.withId(R.id.recycler_view))
               .perform(
                   RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                       3,
                       click()
                   )
               )
           Thread.sleep(1000)
            Espresso.unregisterIdlingResources(idlingResource)
    }

    @Test
    fun showProductDetailTest(){
        val intent: Intent = Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ProductSearchActivity::class.java)
        intent.putExtra(ProductDetailFragment.FLAG_BAR_CODE, "8888826016120")

        mActivityTestRule.launchActivity(intent)
        mActivityTestRule.activity.runOnUiThread {
            val fragment = ProductDetailFragment.newInstance()
            val bundle = Bundle()
            bundle.putString(ProductDetailFragment.FLAG_BAR_CODE, "8888826016120") //key and value
            fragment.setArguments(bundle)
            mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.root_layout, fragment).commitNow()
        }
    }
}