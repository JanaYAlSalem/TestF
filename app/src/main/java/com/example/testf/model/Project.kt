package com.example.testf.model

data class Project (val title : String = "",
                    val description : String = "",
                    val location : String= "",
                    val userId : String = "",
                    val projectId : String = "",
                    val listRequestProject: List<RequestProject> = emptyList())