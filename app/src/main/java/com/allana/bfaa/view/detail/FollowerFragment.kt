package com.allana.bfaa.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allana.bfaa.R
import com.allana.bfaa.adapter.detail.FollowerUserAdapter
import com.allana.bfaa.model.detail.ResponseFollowerUser
import com.allana.bfaa.viewmodel.detail.ViewModelFollower
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {

    private lateinit var viewModelFollower: ViewModelFollower

    // todo 3
    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo 5
        val username = arguments?.getString(ARG_USERNAME)

        viewModelFollower = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ViewModelFollower::class.java)

        username?.let { attachObserve(it) }
    }

    private fun attachObserve(username: String) {
        viewModelFollower.responseFollowerUser.observe(viewLifecycleOwner, {
            showFollowerUser(it)
        })
        viewModelFollower.getFollowerUser(username)
    }

    private fun showFollowerUser(responseFollowerUser: List<ResponseFollowerUser>) {
        val adapter = context?.let { FollowerUserAdapter(responseFollowerUser) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter?.setOnItemClickCallback(object: FollowerUserAdapter.OnItemClickCallback{
            override fun onItemClicked(listUser: ResponseFollowerUser) {
                showSelectedUser(listUser)
            }

        })
    }

    private fun showSelectedUser(listUser: ResponseFollowerUser) {
        val intentToDetail = Intent(context, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, listUser.login)
        startActivity(intentToDetail)
    }

}