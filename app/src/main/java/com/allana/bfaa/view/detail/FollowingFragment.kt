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
import com.allana.bfaa.adapter.detail.FollowingUserAdapter
import com.allana.bfaa.model.detail.ResponseFollowingUser
import com.allana.bfaa.viewmodel.detail.ViewModelFollowing
import kotlinx.android.synthetic.main.fragment_following.*

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {

    private lateinit var viewModelFollowing: ViewModelFollowing

    // todo 3
    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo 5
        val username = arguments?.getString(ARG_USERNAME)

        viewModelFollowing = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ViewModelFollowing::class.java)

        username?.let{ attachObserve(it) }
    }

    private fun attachObserve(username: String) {
        viewModelFollowing.responseFollowingUser.observe(viewLifecycleOwner, {
            showFollowingUser(it)
        })
        viewModelFollowing.getFollowingUser(username)
    }

    private fun showFollowingUser(responseFollowingUser: List<ResponseFollowingUser>) {
        val adapter = context?.let { FollowingUserAdapter(responseFollowingUser) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter?.setOnItemClickCallback(object: FollowingUserAdapter.OnItemClickCallback{
            override fun onItemClicked(listUser: ResponseFollowingUser) {
                showSelectedUser(listUser)
            }

        })
    }

    private fun showSelectedUser(listUser: ResponseFollowingUser) {
        val intentToDetail = Intent(context, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, listUser.login)
        startActivity(intentToDetail)
    }
}