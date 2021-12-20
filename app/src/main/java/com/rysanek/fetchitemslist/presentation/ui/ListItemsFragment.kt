package com.rysanek.fetchitemslist.presentation.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.fetchitemslist.R
import com.rysanek.fetchitemslist.data.util.Constants.NO_CONNECTION
import com.rysanek.fetchitemslist.data.util.DownloadState
import com.rysanek.fetchitemslist.databinding.FragmentListItemsBinding
import com.rysanek.fetchitemslist.presentation.adapters.ItemsRecyclerViewAdapter
import com.rysanek.fetchitemslist.presentation.utils.gone
import com.rysanek.fetchitemslist.presentation.utils.show
import com.rysanek.fetchitemslist.presentation.utils.showSnackBar
import com.rysanek.fetchitemslist.presentation.viewmodels.ListItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemsFragment : Fragment() {
    
    private val viewModel: ListItemViewModel by viewModels()
    private var _rvAdapter: ItemsRecyclerViewAdapter? = null
    private val rvAdapter get() = _rvAdapter!!
    private var _binding: FragmentListItemsBinding? = null
    private val binding get() = _binding!!
    private var popupMenu: PopupMenu? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        
        _binding = FragmentListItemsBinding.inflate(inflater, container, false)
        
        setupRecyclerview()
        
        setupOptionsMenu()
    
        trackItemsListFromDB()
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        with(viewModel) {
            
            if (!hasInternetConnection()) showSnackBar(NO_CONNECTION)
            
            downloadState.observe(viewLifecycleOwner) { state -> handleDownloadState(state) }
        }
    }
    
    private fun setupRecyclerview() {
        binding.rvItemsList.apply {
            
            _rvAdapter = ItemsRecyclerViewAdapter()
            
            rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            
            adapter = rvAdapter
            
            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
            
            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }
    
    /**
     * Dynamically tracks the list of items from the db and automatically changes the list
     * when a filter is requested**/
    private fun trackItemsListFromDB() {
        viewModel.sortedList.observe(viewLifecycleOwner) { data ->
            data.observe(viewLifecycleOwner) { liveData ->
                liveData.observe(viewLifecycleOwner) { list -> rvAdapter.setData(list) }
            }
        }
    }
    
    /**
     * Handles the UI during the different states of updating data from the server.
     * @param state [DownloadState].
     */
    private fun handleDownloadState(state: DownloadState?) {
        when (state) {
            is DownloadState.Idle -> { binding.progressBar.gone() }
            is DownloadState.Downloading -> { binding.progressBar.show() }
            is DownloadState.Finished -> { binding.progressBar.gone() }
            is DownloadState.Error -> { binding.progressBar.gone() }
            else -> { /* NO-OP */ }
        }
    }
    
    private fun setupOptionsMenu() {
        
        binding.ivFilter.setOnClickListener {
            
            popupMenu = PopupMenu(requireContext(), it).apply {
                inflate(R.menu.filter_menu)
                setForceShowIcon(true)
                setOnMenuItemClickListener { menuItem ->
                    setupOnItemClick(menuItem)
                    true
                }
                show()
            }
        }
    }
    
    private fun setupOnItemClick(menuItem: MenuItem) {
        val itemIdFilter = when (menuItem.itemId) {
            R.id.miGroup1 -> 1
            R.id.miGroup2 -> 2
            R.id.miGroup3 -> 3
            R.id.miGroup4 -> 4
            else -> null
        }
        
        viewModel.setSortedList(itemIdFilter)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _rvAdapter = null
        popupMenu = null
    }
    
}