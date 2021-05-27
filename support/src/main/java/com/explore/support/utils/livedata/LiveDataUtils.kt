package com.explore.support.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.explore.support.store.models.test.DashboardResponseEntity

//val dashboardResponseLocal = Transformations.map(db.getDashboardDao().getDashboard()){
//    DashboardResponse(
//        it.status,
//        it.limit,
//        emptyList(),
//        emptyList(),
//        null
//    )
//}
//
//fun LiveData<DashboardResponseEntity>.transformTo(){
//    this.Transformations.map(db.getDashboardDao().getDashboard()){
//        DashboardResponse(
//            it.status,
//            it.limit,
//            emptyList(),
//            emptyList(),
//            null
//        )
//    }
//}