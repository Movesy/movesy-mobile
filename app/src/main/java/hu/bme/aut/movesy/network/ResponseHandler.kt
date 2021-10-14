package hu.bme.aut.movesy.network
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResponseHandler<T> (val lambda: () -> Unit): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.isSuccessful){
            Log.d("network", "Request successful")
            lambda()
        } else {
            Log.w("network", "request failed: ${response.message()}")
        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.w("network", "request failed: ${t.cause}")
    }
}