package com.mudassir.mydonations.UI.Main.DonationFragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import com.mudassir.mydonations.databinding.ActivityAddRequestBinding
import kotlinx.coroutines.launch

class AddRequestActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddRequestBinding
    lateinit var viewModel: RequestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = RequestViewModel()

        lifecycleScope.launch {
            viewModel.isSucessfullySaved.collect {
                it?.let {
                    if (it == true) {
                        Toast.makeText(
                            this@AddRequestActivity,
                            "Successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isFailure.collect {
                it?.let {
                    Toast.makeText(this@AddRequestActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val Receivername = binding.titleInput.text.toString().trim()
            val ReceiverNumber = binding.Pnumber.text.toString()
            val description = binding.descriptionInput.text.toString().trim()
            val RequiredAmount = binding.AmountInput.text.toString().trim()
//            val dated = binding.date.text.toString().trim()


            // Validate the input fields
            if (Receivername.isEmpty() || ReceiverNumber.isEmpty() || description.isEmpty() || RequiredAmount.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = RequiredAmount.toIntOrNull()

            if (amount == null) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request= RequestModelClass()
            request.rname=Receivername
            request.rphone=ReceiverNumber
            request.rdesc=description
            request.rAmount=amount

            viewModel.addRequest(request)
            Toast.makeText(this, "Request Added Successfully!", Toast.LENGTH_SHORT).show()
            finish()


        }
    }
}