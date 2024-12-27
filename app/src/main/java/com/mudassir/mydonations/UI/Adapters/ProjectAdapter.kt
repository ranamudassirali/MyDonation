package com.mudassir.mydonations.UI.Adapters.ProjectAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.mudassir.mydonations.UI.Main.DonationFragment.DonationAmountActivity
import com.mudassir.mydonations.UI.ModelClasses.Donation
import com.mudassir.mydonations.UI.ModelClasses.HistoryModelClass
import com.mudassir.mydonations.UI.ModelClasses.RequestModelClass
import com.mudassir.mydonations.UI.ViewHolder.HistoryViewHolder
import com.mudassir.mydonations.UI.ViewHolder.ProjectViewHolder
import com.mudassir.mydonations.UI.ViewHolder.RequestViewHolder
import com.mudassir.mydonations.databinding.HistoryDesignBinding
import com.mudassir.mydonations.databinding.ItemProjectBinding
import com.mudassir.mydonations.databinding.RequestDesignBinding

class ProjectAdapter(val donors:List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val binding =
                ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProjectViewHolder(binding)
        } else if (viewType == 1) {
            val binding =
                RequestDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RequestViewHolder(binding)
        } else {
           val binding=HistoryDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
           return HistoryViewHolder(binding)
        }

    }


    override fun getItemCount(): Int {
        return donors.size
    }

    override fun getItemViewType(position: Int): Int {
        if (donors.get(position) is Donation) return 0
        if (donors.get(position) is RequestModelClass) return 1
        if (donors.get(position) is HistoryModelClass) return 2
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ProjectViewHolder) {
//            val project = donors.get(position) as Donation
//            holder.binding..text = project.personName
//            holder.binding.email.text = project.personEmail
//            holder.binding.number.text = project.phoneNumber
//            holder.binding.location.text = project.location
//            holder.binding.amount.text = project.Amount.toString()





            holder.itemView.setOnClickListener {
//                holder.itemView.context.startActivity(
//                    Intent(
//                        holder.itemView.context,
//                        ProjectDetailsActivity::class.java
//                    ).putExtra("id", project.id)
//                )
            }

        }
        if (holder is RequestViewHolder) {
            val project1 = donors.get(position) as RequestModelClass
            holder.binding.aname.text = project1.rname
            holder.binding.anumber.text = project1.rphone
            holder.binding.adesc.text = project1.rdesc
            holder.binding.aamount.text = project1.rAmount.toString()
            holder.binding.adate.text = project1.rdate


            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(
                        holder.itemView.context,
                        DonationAmountActivity::class.java
                    ).putExtra("data", Gson().toJson(project1))
                )
            }


        }

        if(holder is HistoryViewHolder){
            val project2 = donors.get(position) as HistoryModelClass
            holder.binding.aname.text = project2.req?.rname
            holder.binding.anumber.text = project2.req?.rphone
            holder.binding.adesc.text = project2.req?.rdesc
            holder.binding.adate.text = project2.req?.rdate
            holder.binding.ddamount.text = project2.req?.rAmount.toString()
            holder.binding.ddamount2.text = project2.hAmount.toString()


        }
    }
}