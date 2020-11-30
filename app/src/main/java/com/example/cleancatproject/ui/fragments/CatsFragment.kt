package com.example.cleancatproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.FragmentCatsBinding
import com.example.cleancatproject.model.TypeEnum
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.ui.pagedList.catsAdapter.CatAdapter
import com.example.cleancatproject.utils.CAT_ID
import com.example.cleancatproject.utils.IMG_TYPE
import com.example.cleancatproject.utils.nextFragment
import com.example.cleancatproject.viewmodel.CatsFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CatsFragment : Fragment(R.layout.fragment_cats), CatAdapter.Listener {
    //инжектим сюда фактори (которая провайдится в CatsFragmentModule)
    //больше ничего во фрагмент инжектить не надо. Всё взамодействие - через вьюмодель.
    //во вьюмодели уже можно инжектить apiService, dataSource и что угодно.
    //в фрагменте логика только для UI. подписаться на вьюмодель, показать список.
    //если по клику нужно перейти на другой экран, то нужно делать это из фрагмента, не из адаптера или вьюмодели.
    //если по клику нужно делать запрос или какую-то логику, то надо стараться выносить это во вьюмодель.
    //взаимодействие фрагмент->вьюмодель через вызов метода вьюмодели.
    // взаимодействие вьюмодель->фрагмент через лайвдату. Фрагмент на неё подписывается.
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var mBinding: FragmentCatsBinding
    private lateinit var mGifBtn: Button
    private lateinit var mJpgBtn: Button
    private lateinit var mPngBtn: Button
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: CatAdapter
    private val mCatsFragmentViewModel: CatsFragmentViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //в этот момент происходит инжект зависимостей в фрагмент
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCatsBinding.inflate(layoutInflater)
        adapter = CatAdapter(this)
        mGifBtn = mBinding.gifBtnId
        mJpgBtn = mBinding.jpgBtnId
        mPngBtn = mBinding.pngBtnId
        mRecyclerView = mBinding.recyclerViewId
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mCatsFragmentViewModel.catPagedListLiveData.observe(this) {
            adapter.submitList(it)
        }
        mGifBtn.setOnClickListener {
            IMG_TYPE = TypeEnum.GIF
            nextFragment(R.id.fragmentWindowId, CatsFragment())
        }
        mJpgBtn.setOnClickListener {
            IMG_TYPE = TypeEnum.JPG
            nextFragment(R.id.fragmentWindowId, CatsFragment())
        }
        mPngBtn.setOnClickListener {
            IMG_TYPE = TypeEnum.PNG
            nextFragment(R.id.fragmentWindowId, CatsFragment())
        }
    }

    override fun onImageClicked(cat: Cat) {
        CAT_ID = cat.id
        val catDetailsFragment = CatDetailsFragment()
        nextFragment(R.id.fragmentWindowId, catDetailsFragment)
    }

    override fun onAddToFavoritesClicked(cat: Cat) {
        //прокидываем обработку клика во вьюмодель, чтобы не использовать apiService из фрагмента
        mCatsFragmentViewModel.onAddToFavoritesClicked(cat)
        Toast.makeText(requireContext(), R.string.added, Toast.LENGTH_SHORT).show()
    }
}