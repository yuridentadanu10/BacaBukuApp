package com.yuridentadanu.bacabukuapp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.view.viewmodel.HomeVM
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    private lateinit var VM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VM = ViewModelProvider(this).get(HomeVM::class.java)

        (activity as MainActivity).viewModel.getToolbarTitle()
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    textView_fragment_home_1_1.text = it

                }

            })

        bottomNavigationView_home_fragment_3.setOnNavigationItemSelectedListener { p0 ->
            var fragment: Fragment = BookListFragment()

            when (p0.itemId) {
                R.id.item_bottomnavigation_manu_1 -> {
                    fragment = BookListFragment()
                    (activity as MainActivity).viewModel.setToolbarTitle("UpToDate Book")

                }
                R.id.item_bottomnavigation_manu_2 -> {
                    fragment = GenreListFragment()
                    (activity as MainActivity).viewModel.setToolbarTitle("Genre List")

                }
            }

            (activity as MainActivity).replaceFragment(
                R.id.frameLayout_fragment_home_2,
                fragment,
                VM.getUIScope()
            )

            true
        }
        bottomNavigationView_home_fragment_3.selectedItemId = R.id.item_bottomnavigation_manu_1
    }

    override fun onDestroy() {
        VM.getJob().cancel()
        super.onDestroy()
    }

}
