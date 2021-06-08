package com.irise.damagedetection

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.irise.damagedetection.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var dbReference: DatabaseReference? = null
    var db: FirebaseDatabase? = null
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        dbReference = db?.reference!!.child("profile")

        register()

        binding.tvNext.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun register() {
        binding.tvRegister.setOnClickListener {
            with(binding) {
                when {
                    TextUtils.isEmpty(inputEmail.text.toString()) -> {
                        inputEmail.error = resources.getString(R.string.fill)
                        return@setOnClickListener
                    }
                    TextUtils.isEmpty(inputUsername.text.toString()) -> {
                        inputUsername.error = resources.getString(R.string.fill)
                        return@setOnClickListener
                    }
                    TextUtils.isEmpty(inputPassword.text.toString()) -> {
                        inputPassword.error = resources.getString(R.string.fill)
                        return@setOnClickListener
                    }
                    else -> auth.createUserWithEmailAndPassword(
                        inputEmail.text.toString(),
                        inputPassword.text.toString()
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val currentUser = auth.currentUser
                                val currentDb = dbReference?.child(currentUser?.uid!!)
                                currentDb?.child("email")?.setValue(inputEmail.text.toString())
                                currentDb?.child("username")
                                    ?.setValue(inputUsername.text.toString())
                                currentDb?.child("password")
                                    ?.setValue(inputPassword.text.toString())
                                Toast.makeText(
                                    this@RegisterActivity,
                                    resources.getString(R.string.register_success),
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    resources.getString(R.string.register_failed),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }

        }

    }
}