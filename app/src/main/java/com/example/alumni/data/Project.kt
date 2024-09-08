package com.example.alumni.data

import com.example.alumni.R

data class Project(
    val projectName: Int,val projectDescription: Int
){
    companion object {
        val projects = listOf(
            Project(R.string.project1,R.string.project_description),
            Project(R.string.project2,R.string.project_description),
            Project(R.string.project3,R.string.project_description),
            Project(R.string.project4,R.string.project_description),
            Project(R.string.project5,R.string.project_description),
        )
    }
}
