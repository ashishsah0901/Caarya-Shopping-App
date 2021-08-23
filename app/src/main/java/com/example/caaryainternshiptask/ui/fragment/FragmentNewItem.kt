package com.example.caaryainternshiptask.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.caaryainternshiptask.databinding.FragmentNewItemBinding
import com.example.caaryainternshiptask.db.ShoppingItem
import com.example.caaryainternshiptask.ui.MainActivity.Companion.viewModel
import java.util.*

class FragmentNewItem: Fragment() {

    private var _binding: FragmentNewItemBinding? = null
    private val binding: FragmentNewItemBinding
        get() = _binding!!

    private val args: FragmentNewItemArgs by navArgs()

    private var shoppingItem: ShoppingItem? = null

    private var bitmap: Bitmap? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val handler = registerForActivityResult(ActivityResultContracts.GetContent()){
        val inputStream = requireActivity().contentResolver.openInputStream(it)
        val bmp = BitmapFactory.decodeStream(inputStream)
        bitmap = bmp
        inputStream?.close()
        binding.uploadPictureIV.setImageBitmap(bmp)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.shoppingItem != null) {
            shoppingItem = args.shoppingItem
            binding.productNameET.editText?.setText(shoppingItem!!.name)
            binding.marketPriceET.editText?.setText(shoppingItem!!.marketPrice.toString())
            binding.storePriceET.editText?.setText(shoppingItem!!.storePrice.toString())
            binding.categoryET.editText?.setText(shoppingItem!!.category.toString())
            binding.descriptionET.editText?.setText(shoppingItem!!.description.toString())
            if(shoppingItem!!.image != null) {
                binding.uploadPictureIV.setImageBitmap(shoppingItem!!.image)
            }
        }
        binding.uploadPictureIV.setOnClickListener {
            handler.launch("image/*")
        }
        binding.cancelBT.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.submitBT.setOnClickListener {
            val productName = binding.productNameET.editText?.text?.trim().toString()
            val marketPrice = binding.marketPriceET.editText?.text?.trim().toString()
            val storePrice = binding.storePriceET.editText?.text?.trim().toString()
            val categoryText = binding.categoryET.editText?.text?.toString()
            val category = when (categoryText?.toLowerCase(Locale.ROOT)){
                "footwear" -> {
                    "Footwear"
                }
                "fashion" -> {
                    "Fashion"
                }
                "home and fashion" -> {
                    "Home & Fashion"
                }
                "home & fashion" -> {
                    "Home & Fashion"
                }
                else -> {
                    "Other"
                }
            }
            val description = binding.descriptionET.editText?.text?.toString()
            if(productName.isNotEmpty() && marketPrice.isNotEmpty() && storePrice.isNotEmpty() && marketPrice[0] != '.' && storePrice[0] != '.') {
                val item = ShoppingItem(
                        productName,
                        marketPrice.toLong(),
                        storePrice.toLong(),
                        category,
                        description,
                        bitmap
                )
                if(shoppingItem == null) {
                    viewModel.insert(item)
                } else {
                    shoppingItem!!.name = productName
                    shoppingItem!!.description = description
                    shoppingItem!!.category = category
                    shoppingItem!!.image = bitmap
                    shoppingItem!!.storePrice = storePrice.toLong()
                    shoppingItem!!.marketPrice = marketPrice.toLong()
                    viewModel.update(shoppingItem!!)
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG).show()
            }
        }
    }

}