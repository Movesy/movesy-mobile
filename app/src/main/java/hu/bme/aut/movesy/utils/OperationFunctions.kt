package hu.bme.aut.movesy.utils

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import java.util.*

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

fun <A> performPostOperation(
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit,
) : LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        val responseStatus = networkCall.invoke()
        responseStatus.message?.let { Log.d("debug", it) }
        if (responseStatus.status == Status.SUCCESS || responseStatus.message?.contains("204") == true) {
            Log.d("debug", "calling saveCallResult: ${responseStatus}")
            responseStatus.data?.let {Log.d("debug", "calling saveCallResult")}
            responseStatus.data?.let { saveCallResult(it) }
        }
        emit(responseStatus)
    }

fun <A> performPostOperationWithUnsafeBody(
    networkCall: suspend () -> Resource<A?>,
    saveCallResult: suspend (A?) -> Unit,
) : LiveData<Resource<A?>> =
    liveData(Dispatchers.IO) {
        val responseStatus = networkCall.invoke()
        responseStatus.message?.let { Log.d("debug", it) }
        if (responseStatus.status == Status.SUCCESS) {
            saveCallResult(responseStatus.data)
        }
        emit(responseStatus)
    }

fun getcurrentDateAndTime(): String {
    val c = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return simpleDateFormat.format(c)
}

fun convertToSimpleDateFormat(date: String): String {
    val splittedString = date.split("-").toMutableList()
    val c = Calendar.getInstance()
    if(splittedString[2].length < 2) splittedString[2] = "0${splittedString[2]}"
    c.set(splittedString[0].toInt(), splittedString[1].toInt(), splittedString[2].substring(0,2).toInt())
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return simpleDateFormat.format(c.time)
}

fun sizeSorter(size: String, sizeToSort: String): Boolean{
    return when(size){
        "SMALL" -> sizeToSort == "SMALL"
        "MEDIUM" -> sizeToSort == "SMALL" || sizeToSort == "MEDIUM"
        "BIG" -> sizeToSort != "HUGE"
        else -> true
    }
}


