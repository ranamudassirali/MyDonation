package com.mudassir.mydonations.UI.Main.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mudassir.mydonations.UI.Adapters.ProjectAdapter.ProjectAdapter
import com.mudassir.mydonations.UI.ModelClasses.Donation
import com.mudassir.mydonations.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    lateinit var adapter: ProjectAdapter
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeFragmentViewModel
    val items=ArrayList<Donation>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.displayname.editText?.setText(currentUser!!.displayName)
        binding.email.editText?.setText(currentUser!!.email)

        viewModel= HomeFragmentViewModel()
        lifecycleScope.launch {
            viewModel.isfailure.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.donorList.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}