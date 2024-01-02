package com.example.astropedia.ui.menuGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.astropedia.data.model.RankingModel
import com.example.astropedia.databinding.CardListRankingBinding
import kotlin.coroutines.coroutineContext

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    private var listRanking = ArrayList<RankingModel>()

    fun setListRanking(listRanking : List<RankingModel>) {
        this.listRanking = listRanking as ArrayList<RankingModel>
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CardListRankingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardListRankingBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return listRanking.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = position + 1
        holder.binding.tvUserPoint.text = listRanking[position].totalPoint + " Points"
        holder.binding.tvDurasi.text = "Durasi penyelesaian: " +listRanking[position].durasi + " Detik"
        holder.binding.tvNumber.text = number.toString()
        listRanking[position].user?.forEach {
            holder.binding.tvUser.text = it?.namaLengkap
            Glide.with(holder.itemView).load(it?.photo).into(holder.binding.ivAva)
        }
    }

}