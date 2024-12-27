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
import com.mudassir.mydonations.UI.Adapters.ProjectAdapter.ProjectAdapter
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import com.mudassir.mydonations.databinding.FragmentDonateBinding
import kotlinx.coroutines.launch

class DonateFragment : Fragment() {
    lateinit var adapter: ProjectAdapter
    lateinit var binding: FragmentDonateBinding
    lateinit var viewModel: DonateFragmentViewModel
    val items1=ArrayList<RequestModelClass>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDonateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("test1", "Test")
        adapter= ProjectAdapter(items1)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        viewModel= DonateFragmentViewModel()
        lifecycleScope.launch {
            viewModel.isfailure.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.ReqdonorList.collect {
                it?.let {
                    items1.clear()
                    items1.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    
}