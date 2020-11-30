package com.example.cleancatproject.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.nextFragment(layout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(layout, fragment).commit()
}

fun Fragment.nextFragment(layout: Int, fragment: Fragment) {
    this.fragmentManager?.beginTransaction()?.replace(layout, fragment)?.addToBackStack(null)
        ?.commit()
}
