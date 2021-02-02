package com.riahi.developerlistapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.riahi.developerlistapp.R
import com.riahi.developerlistapp.data.Developer
import com.riahi.developerlistapp.ui.viewmodels.SharedViewModel
import timber.log.Timber


class MainFragment : Fragment(), MainRecyclerAdapter.DeveloperItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var mainAdapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.developerData.observe(viewLifecycleOwner, Observer
        {
            mainAdapter = MainRecyclerAdapter(requireContext(), it, this)
            Timber.d("data $it")
            recyclerView.adapter = mainAdapter
            swipeLayout.isRefreshing = false
        })

        viewModel.activityTitle.observe(viewLifecycleOwner, Observer {
            requireActivity().title = it
        })

        return view
    }

    override fun onItemClick(developer: Developer) {
        Timber.i("Selected Dev: ${developer.name}")
        viewModel.selectedDev.value = developer
        navController.navigate(R.id.action_mainFragment_to_detailFragment)
    }
}
