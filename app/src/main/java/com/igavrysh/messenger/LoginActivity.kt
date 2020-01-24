package com.igavrysh.messenger

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        return@addOnCompleteListener
                    }

                    Log.d(
                        "Main",
                        "Successfully created user with uid: $it.result.user.uid")

                    Toast.makeText(
                        this,
                        "Successfull login for user with uid: $it.result.user.uid",
                        Toast.LENGTH_SHORT)
                        .show()

                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to create user: ${it.message}")
                    Toast.makeText(
                        this,
                        "Failed to login user: ${it.message}",
                        Toast.LENGTH_SHORT)
                        .show()
                }
        }

        back_to_register_textview.setOnClickListener {
            finish()
        }
    }

}