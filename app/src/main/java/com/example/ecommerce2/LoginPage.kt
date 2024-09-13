package com.example.ecommerce2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginPage : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        edtEmail = findViewById(R.id.edt_email)
        edtPass = findViewById(R.id.edt_pass)
        loginBtn = findViewById(R.id.login_btn)
        signupBtn = findViewById(R.id.signup_btn)
        auth = Firebase.auth

        loginBtn.setOnClickListener{
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            logIn(email, pass)
        }

        signupBtn.setOnClickListener{
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()
            signUp(email, pass)
        }
    }

    private fun signUp(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(
                        Intent(this, UploadProduActivity::class.java)
                    )
                    Toast.makeText(this,"Signed up Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun logIn(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(
                        Intent(this, UploadProduActivity::class.java)
                    )
                    Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}