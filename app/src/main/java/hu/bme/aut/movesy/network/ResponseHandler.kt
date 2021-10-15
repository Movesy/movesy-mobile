package hu.bme.aut.movesy.network
import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResponseHandler<T> (
    private val context: Context,
    private val onSuccess: (res: T) -> Unit = { },

    ): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.isSuccessful){
            Log.d("network", "Request successful")
            Log.d("network", response.body().toString())
            onSuccess(response.body()!!)
        } else {
            Toast.makeText(context,"Error while connecting to server:${response.message()}", Toast.LENGTH_LONG).show()
        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Toast.makeText(context, "No internet connection: ${t.message.toString()}", Toast.LENGTH_LONG).show()
        Log.d("network failure message", t.message.toString())
        Log.d("network failure cause", t.cause.toString())
        Log.d("network failure", t.toString())
    }
}