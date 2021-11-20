package hu.bme.aut.movesy.viewmodel

import android.icu.text.SimpleDateFormat
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
        if (responseStatus.status == Status.SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }
        }
        emit(responseStatus)
    }

fun getcurrentDateAndTime(): String {
    val c = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return simpleDateFormat.format(c)
}
