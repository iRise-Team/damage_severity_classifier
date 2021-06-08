package com.irise.damagedetection

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.irise.damagedetection.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        login()

        binding.tvNext.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        with(binding) {
            tvBtnLogin.setOnClickListener {
                when {
                    TextUtils.isEmpty(inputEmail.text.toString()) -> {
                        inputEmail.error = resources.getString(R.string.fill)
                        return@setOnClickListener
                    }
                    TextUtils.isEmpty(inputPassword.text.toString()) -> {
                        inputPassword.error = resources.getString(R.string.fill)
                        return@setOnClickListener
                    }
                    else -> {
                        auth.signInWithEmailAndPassword(
                            inputEmail.text.toString(),
                            inputPassword.text.toString()
                        )
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        resources.getString(R.string.login_success),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        resources.getString(R.string.login_failed),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                }
            }
        }
    }
}