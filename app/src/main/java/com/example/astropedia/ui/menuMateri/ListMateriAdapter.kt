package com.example.astropedia.ui.menuMateri

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astropedia.data.model.MateriModel
import com.example.astropedia.databinding.CardListMateriBinding

class ListMateriAdapter : RecyclerView.Adapter<ListMateriAdapter.ViewHolder>() {

    private var listMateri = ArrayList<MateriModel>()

    fun setListMateri(listMateri : List<MateriModel>) {
        this.listMateri = listMateri as ArrayList<MateriModel>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding : CardListMateriBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardListMateriBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Glide.with(holder.itemView).load("https://tata-surya.skripsijoss.my.id/public/icon/"+listMateri[position].icon)
           .into(holder.binding.rvIconMateri)
        holder.binding.rvTitleMateri.text = listMateri[position].nama
        holder.binding.rvMateriDescSingkat.text = listMateri[position].miniDeskripsi

        holder.binding.cardMateri.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(v?.context, DetailMateriActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("idMateri", listMateri[position].id)
            v?.context?.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return listMateri.size
    }

}