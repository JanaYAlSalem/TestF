package com.example.testf.model

data class RequestProject (val userId : String = "",
                           val projectId : String = "",
                           val jobTitle : String = "",
                           val description : String = "",
                           val reqId : String = "" ,
                           val stateOfRequest : RequestState = RequestState.WAITING)