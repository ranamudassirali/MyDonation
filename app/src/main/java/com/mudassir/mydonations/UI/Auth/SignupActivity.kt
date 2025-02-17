package com.mudassir.mydonations.UI.Auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mudassir.mydonations.UI.Main.HomeFragment.MainActivity
import com.mudassir.mydonations.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch


class SignupActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding:ActivitySignupBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = AuthViewModel()
//        viewModel.checkUser()

        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Please wait while we check create your account...")
        progressDialog.setCancelable(false)



        lifecycleScope.launch {
            viewModel.currentUser.collect {
                if (it != null) {
                    progressDialog.dismiss()
                    startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                progressDialog.dismiss()
                if (it!=null) {
                    Toast.makeText(this@SignupActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginTxt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        binding.signupbtn.setOnClickListener {
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            val confirmpassword = binding.confirmPassword.editText?.text.toString()
            val name = binding.name.editText?.text.toString()

            if (name.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!password.equals(confirmpassword)) {
                Toast.makeText(this, "Confirm Password does not match with password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            progressDialog.show()

            viewModel.signUp(email,password,name)



        }


    }
}