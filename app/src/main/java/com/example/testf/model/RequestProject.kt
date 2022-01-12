package com.example.testf.model

data class RequestProject (val reqId : String = "" ,
                           val userId : String = "",
                           val projectId : String = "",
                           val jobTitle : String = "",
                           val description : String = "",
                           val stateOfRequest : RequestState = RequestState.WAITING)