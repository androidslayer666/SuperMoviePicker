package com.example.supermoviepicker.ui.parts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.supermoviepicker.ui.ListUpdater
import com.example.supermoviepicker.viewModel.MovieViewModel
import com.example.supermoviepicker.viewModel.SortingOptions
import com.example.supermoviepicker.R
import com.example.supermoviepicker.databinding.FragmentFilterSortingBinding


class FilterSortingFragment : Fragment(R.layout.fragment_filter_sorting) {

    val viewModel: MovieViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        val movieFragment =
            parentFragmentManager.findFragmentById(R.id.fragment_container_view) as ListUpdater

        val binding = FragmentFilterSortingBinding.inflate(layoutInflater)
                binding.buttonFilters.setOnClickListener {
                    FilterDialog().show(parentFragmentManager, "filters")
        }

        binding.buttonSorting.setOnClickListener {
            binding.sortingButtonsLayout.visibility =
                if (binding.sortingButtonsLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        binding.buttonSortingRatingDesc.setOnClickListener {
            viewModel.setSortingOption(SortingOptions.RATINGDESC)
            movieFragment.updateList()
        }

        binding.buttonSortingRatingAsc.setOnClickListener {
            viewModel.setSortingOption(SortingOptions.RATINGASC)
            movieFragment.updateList()
        }

        binding.buttonSortingYearDesc.setOnClickListener {
            viewModel.setSortingOption(SortingOptions.YEARDESC)
            movieFragment.updateList()
        }

        binding.buttonSortingYearAsc.setOnClickListener {
            viewModel.setSortingOption(SortingOptions.YEARASC)
            movieFragment.updateList()
        }

        return binding.root
    }
}