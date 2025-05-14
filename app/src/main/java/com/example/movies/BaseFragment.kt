package com.example.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding: VB
        get() {
            return _binding ?: throw IllegalStateException("ViewBinding is null")
        }
    protected val component by lazy {
        (requireActivity().application as MovieApp).component
    }
    override fun onAttach(context: Context) {
        performInjection()
        super.onAttach(context)
    }
    protected abstract fun performInjection()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}