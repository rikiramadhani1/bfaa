package com.riki.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.riki.githubuser.databinding.FragmentFollowingBinding
import com.riki.githubuser.model.FollowingModel
import com.riki.githubuser.viewmodel.UserViewModel
import com.riki.githubuser.adapter.FollowingAdapter
import java.util.ArrayList

private const val ARG_LOGIN = "login"

class FollowingFragment : Fragment() {

    private var login: String? = null
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_LOGIN)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            userViewModel =
                ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                    UserViewModel::class.java
                )

            userViewModel.isLoadingFragment.observe(requireActivity(), {
                setLoading(it)
            })

            login?.let {
                userViewModel.getFollowing(it).observe(requireActivity(), { listFollowing ->
                    setupListFollowing(listFollowing)
                })
            }

        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.rvFollowing.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvFollowing.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupListFollowing(list: List<FollowingModel>) {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val adapter = FollowingAdapter(list as ArrayList<FollowingModel>)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)


        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, param1)
                }
            }
    }

}