package com.example.supermoviepicker.viewModel

data class FilterItem (
    var min: Int? = null,
    var max: Int? = null,
    var allowed: Boolean? = null
)

class FilterMovies{
    var filterIfAdult =  FilterItem()
    var rating = FilterItem()
    var years = FilterItem()
    var minReview = FilterItem()
}

enum class SortingOptions {
    RATINGDESC, RATINGASC, YEARDESC, YEARASC
}

enum class FilterOption {
    MINRATING, MAXRATING, MINYEAR, MAXYEAR, MINREVIEW, ADULT
}