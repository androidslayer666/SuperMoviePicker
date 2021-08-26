package com.example.supermoviepicker.ui.parts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment


import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.supermoviepicker.Constants.FILTER_RATING_INITIAL
import com.example.supermoviepicker.Constants.FILTER_RATING_MAX
import com.example.supermoviepicker.Constants.FILTER_RATING_MIN
import com.example.supermoviepicker.Constants.FILTER_REVIEW_INITIAL
import com.example.supermoviepicker.Constants.FILTER_REVIEW_MAX
import com.example.supermoviepicker.Constants.FILTER_REVIEW_MIN
import com.example.supermoviepicker.Constants.FILTER_YEAR_INITIAL
import com.example.supermoviepicker.Constants.FILTER_YEAR_MAX
import com.example.supermoviepicker.Constants.FILTER_YEAR_MIN
import com.example.supermoviepicker.ui.ListUpdater
import com.example.supermoviepicker.viewModel.FilterOption
import com.example.supermoviepicker.viewModel.MovieViewModel
import com.example.supermoviepicker.R
import com.example.supermoviepicker.databinding.FilterDialogBinding


class FilterDialog : DialogFragment() {

    val viewModel: MovieViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val builder = AlertDialog.Builder(it)

            val binding: FilterDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(
                    context
                ), R.layout.filter_dialog, null, false
            )
            builder.setView(binding.root)

            // Setting ranges for number pickers
            setRanges(binding)

            // Retrieving the values or setting default ones
            getPreviousValuesOrSetDefault(binding, savedInstanceState)

            // Setting listeners for pickers cause properties stored in object.
            setListeners(binding)

            builder.setMessage("Filters")
                .setPositiveButton(
                    "Apply"
                ) { _, _ ->
                    (parentFragmentManager.findFragmentById(R.id.fragment_container_view) as ListUpdater)
                        .updateList()
                }
                .setNegativeButton(
                    "Discard"
                ) { _, _ ->
                    viewModel.clearFilter()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun getPreviousValuesOrSetDefault(
        binding: FilterDialogBinding,
        savedInstanceState: Bundle?
    ) {
        binding.checkBoxAdult.isChecked = viewModel.filterMovies.filterIfAdult.allowed
            ?: savedInstanceState?.getBoolean("ADULT") ?: false
        binding.inputStartYear.value =
            viewModel.filterMovies.years.min ?: savedInstanceState?.getInt("MINYEAR")
                    ?: FILTER_YEAR_INITIAL
        binding.inputEndYear.value =
            viewModel.filterMovies.years.max ?: savedInstanceState?.getInt("MAXYEAR")
                    ?: FILTER_YEAR_MAX
        binding.inputMaxRating.value = viewModel.filterMovies.rating.max
            ?: savedInstanceState?.getInt("MAXRATING") ?: FILTER_RATING_MAX
        binding.inputMinRating.value = viewModel.filterMovies.rating.min
            ?: savedInstanceState?.getInt("MINRATING") ?: FILTER_RATING_INITIAL
        binding.seekMinReviewNumber.progress = viewModel.filterMovies.minReview.min
            ?: savedInstanceState?.getInt("MINREVIEW") ?: FILTER_REVIEW_INITIAL
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRanges(binding: FilterDialogBinding) {
        binding.apply {
            binding.inputStartYear.maxValue = FILTER_YEAR_MAX
            binding.inputStartYear.minValue = FILTER_YEAR_MIN
            binding.inputEndYear.maxValue = FILTER_YEAR_MAX
            binding.inputEndYear.minValue = FILTER_YEAR_MIN
            binding.inputMinRating.maxValue = FILTER_RATING_MAX
            binding.inputMinRating.minValue = FILTER_RATING_MIN
            binding.inputMaxRating.maxValue = FILTER_RATING_MAX
            binding.inputMaxRating.minValue = FILTER_RATING_MIN
            binding.seekMinReviewNumber.max = FILTER_REVIEW_MAX
            binding.seekMinReviewNumber.min = FILTER_REVIEW_MIN        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val filterMovies = viewModel.filterMovies
        filterMovies.rating.min?.let { outState.putInt("MINRATING", it) }
        filterMovies.rating.max?.let { outState.putInt("MAXRATING", it) }
        filterMovies.years.min?.let { outState.putInt("MINYEAR", it) }
        filterMovies.years.max?.let { outState.putInt("MAXYEAR", it) }
        filterMovies.minReview.min?.let { outState.putInt("MINREVIEW", it) }
        filterMovies.filterIfAdult.allowed?.let { outState.putBoolean("ADULT", it) }
        super.onSaveInstanceState(outState)
    }

    private fun setListeners(binding: FilterDialogBinding) {
        binding.apply {
            checkBoxAdult.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.setFilter(isChecked = isChecked, filterOption = FilterOption.ADULT)
            }
            inputMinRating.setOnValueChangedListener { _, _, newVal ->
                viewModel.setFilter(newVal, FilterOption.MINRATING)
            }
            inputMaxRating.setOnValueChangedListener { _, _, newVal ->
                viewModel.setFilter(newVal, FilterOption.MAXRATING)
            }
            inputStartYear.setOnValueChangedListener { _, _, newVal ->
                viewModel.setFilter(newVal, FilterOption.MINYEAR)
            }
            inputEndYear.setOnValueChangedListener { _, _, newVal ->
                viewModel.setFilter(newVal, FilterOption.MAXYEAR)
            }
            seekMinReviewNumber.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int, fromUser: Boolean
                ) {
                    viewModel.setFilter(progress, FilterOption.MINREVIEW)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }
}