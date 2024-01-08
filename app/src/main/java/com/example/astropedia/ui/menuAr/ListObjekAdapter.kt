package com.example.astropedia.ui.menuAr

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astropedia.data.model.MateriModel
import com.example.astropedia.databinding.CardListObjekBinding
import com.example.astropedia.ui.listener.OnClickListener
import com.example.astropedia.ui.menuMateri.DetailMateriActivity

class ListObjekAdapter(private val clickListener: OnClickListener) : RecyclerView.Adapter<ListObjekAdapter.ViewHolder>() {

    private var listObjek = ArrayList<MateriModel>()

    fun setListObjek(listObjek: List<MateriModel>) {
        this.listObjek = listObjek as ArrayList<MateriModel>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : CardListObjekBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardListObjekBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return listObjek.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://tata-surya.skripsijoss.my.id/public/icon/"+listObjek[position].icon)
            .into(holder.binding.ivCardObjek)
        holder.binding.tvNamaObjek.text = listObjek[position].nama

        holder.binding.cvCardObjek.setOnClickListener {
            clickListener.onItemObjectClick(listObjek[position].nama, listObjek[position].id)
        }
    }
}