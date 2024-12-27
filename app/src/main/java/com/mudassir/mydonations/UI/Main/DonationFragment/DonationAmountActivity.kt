package com.mudassir.mydonations.UI.Main.DonationFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.UI.Main.HistoryCompletedFragment.HistoryViewModel
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import com.mudassir.mydonations.databinding.ActivityDonationAmountBinding
import kotlinx.coroutines.launch

class DonationAmountActivity : AppCompatActivity() {

    private lateinit var currentRequest: RequestModelClass
    private lateinit var binding: ActivityDonationAmountBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var viewModel1: RequestViewModel

    private lateinit var progressDialog: ProgressDialog
    lateinit var user: FirebaseUser
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationAmountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = HistoryViewModel()
        viewModel1 = RequestViewModel()

        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("We update the donation amount...")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.isSucessfullySaved.collect {
                progressDialog.dismiss()
                if (it==true) {
                    Toast.makeText(
                        this@DonationAmountActivity,
                        "Donation successful! Remaining amount: ${currentRequest.rAmount}",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()

                }
            }
        }



        // Retrieve data from intent
        val dataJson = intent.getStringExtra("data")
        if (dataJson != null) {
            currentRequest = Gson().fromJson(dataJson, RequestModelClass::class.java)
        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        authRepository= AuthRepository()
        // Set up donate button click listener
        binding.donateAmount.setOnClickListener {
            handleDonation()


        }

    }

    private fun handleDonation() {
        val donationAmountInput = binding.DonateAmountInput.text.toString()

        // Validate input
        val donationAmount = donationAmountInput.toIntOrNull()
        Toast.makeText(this, "Donation amount: $donationAmountInput", Toast.LENGTH_LONG).show()
        if (donationAmount == null || donationAmount <= 0) {
            Toast.makeText(this, "Enter a valid donation amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Check against remaining amount
        if (donationAmount > (currentRequest.rAmount ?: 0)) {
            Toast.makeText(
                this,
                "Donation amount exceeds remaining amount (${currentRequest.rAmount})",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Subtract the donation amount
        val currentRemaining = currentRequest.rAmount ?: 0
        currentRequest.rAmount = currentRemaining - donationAmount

        progressDialog.show()

        user=authRepository.getCurrentUser()!!
        Log.i("uid", user.uid)
        val historyobj= HistoryModelClass()
        historyobj.hAmount=binding.DonateAmountInput.text.toString().toInt()
        historyobj.hdate=System.currentTimeMillis().toString()
        historyobj.req=currentRequest
        historyobj.hid=user.uid
        viewModel.addHistory(historyobj)

        // Update Firebase or database
        viewModel1.updateRequest(currentRequest)
        // Show success message

        // Optionally clear input
//        binding.donateAmount.text.clear()
    }





    private fun updateRequestInDatabase() {
        // Mock example for Firebase update, replace with your actual implementation
        // FirebaseDatabase.getInstance().getReference("requests")
        //     .child(currentRequest.projectId)
        //     .setValue(currentRequest)
        //     .addOnSuccessListener {
        //         Toast.makeText(this, "Request updated successfully", Toast.LENGTH_SHORT).show()
        //     }
        //     .addOnFailureListener {
        //         Toast.makeText(this, "Failed to update request", Toast.LENGTH_SHORT).show()
        //     }
    }
}
