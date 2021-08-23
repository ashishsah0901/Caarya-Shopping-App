package com.example.caaryainternshiptask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.caaryainternshiptask.R
import com.example.caaryainternshiptask.adapter.ItemAdapter
import com.example.caaryainternshiptask.databinding.FragmentShoppingBinding
import com.example.caaryainternshiptask.db.ShoppingItem
import com.example.caaryainternshiptask.ui.MainActivity.Companion.viewModel

class FragmentShopping: Fragment(), ItemAdapter.OnItemClickListener, ItemAdapter.OnItemClickForDelete {

    private var _binding: FragmentShoppingBinding? = null
    private val binding: FragmentShoppingBinding
        get() = _binding!!

    private lateinit var footWearAdapter: ItemAdapter
    private lateinit var fashionAdapter: ItemAdapter
    private lateinit var homeAndFashionAdapter: ItemAdapter
    private lateinit var otherAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        footWearAdapter = ItemAdapter(this, this)
        fashionAdapter  = ItemAdapter(this, this)
        homeAndFashionAdapter = ItemAdapter(this, this)
        otherAdapter = ItemAdapter(this, this)
        setUpRecyclerView()
        viewModel.allItems.observe(viewLifecycleOwner) {
            val footWearList = mutableListOf<ShoppingItem>()
            val fashionList = mutableListOf<ShoppingItem>()
            val homeAndFashionList = mutableListOf<ShoppingItem>()
            val otherList = mutableListOf<ShoppingItem>()
            for (item in it) {
                when (item.category) {
                    "Footwear" -> {
                        footWearList.add(item)
                    }
                    "Fashion" -> {
                        fashionList.add(item)
                    }
                    "Home & Fashion" -> {
                        homeAndFashionList.add(item)
                    }
                    else -> {
                        otherList.add(item)
                    }
                }
            }
            if(footWearList.isNotEmpty()) {
                footWearAdapter.differ.submitList(footWearList)
                toggleFootwear(true)
            }
            if(fashionList.isNotEmpty()) {
                fashionAdapter.differ.submitList(fashionList)
                toggleFashion(true)
            }
            if(homeAndFashionList.isNotEmpty()) {
                homeAndFashionAdapter.differ.submitList(homeAndFashionList)
                toggleHomeAndFashion(true)
            }
            if(otherList.isNotEmpty()) {
                otherAdapter.differ.submitList(otherList)
                toggleOther(true)
            }
        }
        binding.expandCollapseFootwear.setOnClickListener {
            toggleFootwear(binding.footwearRV.visibility == View.GONE)
        }
        binding.expandCollapseFashion.setOnClickListener {
            toggleFashion(binding.fashionRV.visibility == View.GONE)
        }
        binding.expandCollapseHomeAndLifestyle.setOnClickListener {
            toggleHomeAndFashion(binding.homeAndLifestyleRV.visibility == View.GONE)
        }
        binding.expandCollapseOther.setOnClickListener {
            toggleOther(binding.otherRV.visibility == View.GONE)
        }
        binding.collapseAll.setOnClickListener {
            toggleOther(false)
            toggleHomeAndFashion(false)
            toggleFootwear(false)
            toggleFashion(false)
        }
        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentShopping2_to_fragmentNewItem, Bundle().apply { putSerializable("shopping_item", null) })
        }
    }

    private fun setUpRecyclerView() {
        binding.footwearRV.adapter = footWearAdapter
        binding.fashionRV.adapter = fashionAdapter
        binding.homeAndLifestyleRV.adapter = homeAndFashionAdapter
        binding.otherRV.adapter = otherAdapter
    }

    override fun onClick(item: ShoppingItem) {
        findNavController().navigate(R.id.action_fragmentShopping2_to_fragmentNewItem, Bundle().apply { putSerializable("shopping_item", item) })
    }

    private fun toggleFootwear(show: Boolean) {
        if(show) {
            binding.expandCollapseFootwear.setImageResource(R.drawable.ic_arrow_down)
            binding.footwearRV.isVisible = show
        } else {
            binding.expandCollapseFootwear.setImageResource(R.drawable.ic_forward)
            binding.footwearRV.isVisible = show
        }
    }

    private fun toggleFashion(show: Boolean) {
        if(show) {
            binding.expandCollapseFashion.setImageResource(R.drawable.ic_arrow_down)
            binding.fashionRV.isVisible = show
        } else {
            binding.expandCollapseFashion.setImageResource(R.drawable.ic_forward)
            binding.fashionRV.isVisible = show
        }
    }

    private fun toggleHomeAndFashion(show: Boolean) {
        if(show) {
            binding.expandCollapseHomeAndLifestyle.setImageResource(R.drawable.ic_arrow_down)
            binding.homeAndLifestyleRV.isVisible = show
        } else {
            binding.expandCollapseHomeAndLifestyle.setImageResource(R.drawable.ic_forward)
            binding.homeAndLifestyleRV.isVisible = show
        }
    }

    private fun toggleOther(show: Boolean) {
        if(show) {
            binding.expandCollapseOther.setImageResource(R.drawable.ic_arrow_down)
            binding.otherRV.isVisible = show
        } else {
            binding.expandCollapseOther.setImageResource(R.drawable.ic_forward)
            binding.otherRV.isVisible = show
        }
    }

    override fun onDelete(item: ShoppingItem) {
        if(footWearAdapter.differ.currentList.contains(item)){
            val newList = mutableListOf<ShoppingItem>()
            footWearAdapter.differ.currentList.forEach {
                newList.add(it)
            }
            newList.remove(item)
            footWearAdapter.differ.submitList(newList)
        } else if(fashionAdapter.differ.currentList.contains(item)){
            val newList = mutableListOf<ShoppingItem>()
            fashionAdapter.differ.currentList.forEach {
                newList.add(it)
            }
            newList.remove(item)
            fashionAdapter.differ.submitList(newList)
        } else if(homeAndFashionAdapter.differ.currentList.contains(item)){
            val newList = mutableListOf<ShoppingItem>()
            homeAndFashionAdapter.differ.currentList.forEach {
                newList.add(it)
            }
            newList.remove(item)
            homeAndFashionAdapter.differ.submitList(newList)
        }else if(otherAdapter.differ.currentList.contains(item)){
            val newList = mutableListOf<ShoppingItem>()
            otherAdapter.differ.currentList.forEach {
                newList.add(it)
            }
            newList.remove(item)
            otherAdapter.differ.submitList(newList)
        }
        viewModel.delete(item)
    }

}