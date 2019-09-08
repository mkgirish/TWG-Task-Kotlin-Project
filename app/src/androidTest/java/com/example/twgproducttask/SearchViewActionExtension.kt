package com.example.twgproducttask

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.v7.widget.SearchView
import android.view.View
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class SearchViewActionExtension {
    companion object {
        fun submitText(text: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
                }

                override fun getDescription(): String {
                    return "Set text and submit"
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as SearchView).setQuery(text, true) //submit=true will fire search
                }
            }
        }
        fun typeText(text: String): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
                }

                override fun getDescription(): String {
                    return "Set text"
                }

                override fun perform(uiController: UiController, view: View) {
                    (view as SearchView).setQuery(text, false)
                }
            }
        }
    }
}