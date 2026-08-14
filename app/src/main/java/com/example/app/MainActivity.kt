package com.example.app

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var submit: Button
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 30, 30, 30)

        val title = TextView(this)
        title.text = "My Application"
        title.textSize = 24f

        name = EditText(this)
        name.hint = "Enter your name"

        email = EditText(this)
        email.hint = "Enter your email"

        submit = Button(this)
        submit.text = "SUBMIT"

        result = TextView(this)
        result.textSize = 18f

        layout.addView(title)
        layout.addView(name)
        layout.addView(email)
        layout.addView(submit)
        layout.addView(result)

        submit.setOnClickListener {
            sendDataToApi()
        }

        setContentView(layout)
    }

    private fun sendDataToApi() {

        val userName = name.text.toString().trim()
        val userEmail = email.text.toString().trim()

        if (userName.isEmpty()) {
            result.text = "Please enter your name"
            return
        }

        if (userEmail.isEmpty()) {
            result.text = "Please enter your email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            result.text = "Please enter a valid email"
            return
        }

        result.text = "Sending data..."
        submit.isEnabled = false

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(ApiService::class.java)

            val user = User(userName, userEmail)

            api.createUser(user).enqueue(object : Callback<ApiResponse> {

                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    submit.isEnabled = true

                    if (response.isSuccessful) {
                        result.text = "Success!\nData sent successfully."
                    } else {
                        result.text = "Server Error: ${response.code()}"
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse>,
                    t: Throwable
                ) {
                    submit.isEnabled = true
                    result.text = "Network Error!\nPlease try again."
                }
            })

        } catch (e: Exception) {
            submit.isEnabled = true
            result.text = "Unexpected Error!\nPlease try again."
        }
    }

    interface ApiService {

        @POST("posts")
        fun createUser(
            @Body user: User
        ): Call<ApiResponse>
    }

    data class User(
        val name: String,
        val email: String
    )

    data class ApiResponse(
        val id: Int? = null,
        val name: String? = null,
        val email: String? = null
    )
}
