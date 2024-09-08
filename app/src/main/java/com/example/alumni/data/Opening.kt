package com.example.alumni.data

import com.example.alumni.R

data class Opening(
    val openingName: Int,val openingDescription: Int
){
    companion object {
        val openings = listOf(
            Opening(R.string.opening1,R.string.job_description),
            Opening(R.string.opening2,R.string.job_description),
            Opening(R.string.opening3,R.string.job_description),
            Opening(R.string.opening4,R.string.job_description),
            Opening(R.string.opening5,R.string.job_description),
        )
    }
}

