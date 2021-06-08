package com.irise.damagedetection.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irise.damagedetection.databinding.FragmentExploreBinding
import com.irise.damagedetection.dummy.Dummy

class ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(
                it,
                ViewModelProvider.NewInstanceFactory()
            )[ExploreViewModel::class.java]
        }

        setupRecyclerView(viewModel.getLists())
    }

    private fun setupRecyclerView(data: List<Dummy>) {
        binding.rvExplore.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ExploreAdapter()
        }.also {
            it.adapter.let { adapter ->
                when (adapter) {
                    is ExploreAdapter -> {
                        adapter.setList(data)
                    }
                }
            }
        }
    }
}