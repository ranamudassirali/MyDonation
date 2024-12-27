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
import com.google.firebase.auth.FirebaseUser
import com.mudassir.mydonations.Model.Repositories.AuthRepository
import com.mudassir.mydonations.UI.Adapters.ProjectAdapter.ProjectAdapter
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import com.mudassir.mydonations.databinding.FragmentHistoryBinding
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    lateinit var adapter: ProjectAdapter
    lateinit var binding: FragmentHistoryBinding
    lateinit var viewModel: HistoryFragmentViewModel
        val items2=ArrayList<HistoryModelClass>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("test1", "Test")
        adapter= ProjectAdapter(items2)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager= LinearLayoutManager(context)

        viewModel= HistoryFragmentViewModel()
        fun isUserAdmin(): Boolean {
            val user: FirebaseUser? = AuthRepository().getCurrentUser()
            return user?.email.equals("ranamudassirali9@gmail.com", ignoreCase = true)
        }
        if (isUserAdmin()) {
            viewModel.getAllHistory()
        } else {
        viewModel.gethistory(AuthRepository().getCurrentUser()!!.uid)}

        lifecycleScope.launch {
            viewModel.isfailure.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.HistoryList.collect {
                it?.let {
                    items2.clear()
                    items2.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}