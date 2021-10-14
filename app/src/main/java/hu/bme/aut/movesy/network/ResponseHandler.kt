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
    private val onSuccess: () -> Unit,

): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.isSuccessful){
            Log.d("network", "Request successful")
            onSuccess()
        } else {
            Toast.makeText(context, response.message().toString(), Toast.LENGTH_LONG).show()
        }

    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Toast.makeText(context, t.cause.toString(), Toast.LENGTH_LONG).show()
    }
}