package com.demo.popcornapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Base class for every fragment in the application. Inflates the received [ViewDataBinding] through [layoutRes] and exposes a non-nullable binding.
 */
abstract class PopCornFragment<Binding : ViewDataBinding>(@LayoutRes private val layoutRes: Int) : Fragment() {

    private var binding: Binding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<Binding>(inflater, layoutRes, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        this.binding = binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /**
     * Returns a non-null [Binding] instance or throws an IllegalStateException if it's called after [onDestroyView].
     */
    @Throws(IllegalStateException::class)
    fun requireBinding(): Binding = binding ?: throw IllegalStateException("View already destroyed")
}