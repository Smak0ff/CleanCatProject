package com.example.cleancatproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.ActivityMainBinding
import com.example.cleancatproject.model.TypeEnum
import com.example.cleancatproject.ui.fragments.CatsFragment
import com.example.cleancatproject.ui.fragments.FavoriteFragment
import com.example.cleancatproject.ui.fragments.UploadFragment
import com.example.cleancatproject.utils.IMG_TYPE
import com.example.cleancatproject.utils.nextFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    lateinit var mNavigationBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mNavigationBar = mBinding.bottomNavigationViewId
        setContentView(mBinding.root)
        nextFragment(R.id.fragmentWindowId, CatsFragment())

        mNavigationBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationСatBtnId -> {
                    IMG_TYPE = TypeEnum.GIF
                    nextFragment(R.id.fragmentWindowId, CatsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigationFavoriteBtnId -> {
                    nextFragment(R.id.fragmentWindowId, FavoriteFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigationDownloadsBtnId -> {
                    nextFragment(R.id.fragmentWindowId, UploadFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}