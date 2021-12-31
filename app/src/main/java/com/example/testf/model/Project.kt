package com.example.testf.model

data class Project (val title : String = "",
                    val description : String = "",
                    val location : String= "")

data class RequestProject (val reqId : Int ,
                           val userId : Int,
                           val description : String,
                           val stateOfRequest : RequestState)


data class Profile (val userId : Int,
                    val firstName : String,
                    val lastName : String,
                    val userImgeUrl : String)

enum class RequestState { WAITING, ACCEPT, DECLINED}