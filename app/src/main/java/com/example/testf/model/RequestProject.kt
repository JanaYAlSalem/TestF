package com.example.testf.model

data class RequestProject (val userId : String = "",
                           val projectId : String = "",
                           val jobTitle : String = "",
                           val description : String = "",
                           val reqId : String = "" ,
                           var stateOfRequest : RequestState = RequestState.WAITING)