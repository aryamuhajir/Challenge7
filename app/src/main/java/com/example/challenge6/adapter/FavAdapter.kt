package com.example.challenge6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.challenge6.R
import com.example.challenge6.room.Favorite
import kotlinx.android.synthetic.main.item_film_adapter.view.*

class FavAdapter(private var onClick : (Favorite)->Unit) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    private var datafilm : List<Favorite>? = null

    fun setDataFilm(film : List<Favorite>){
        this.datafilm = film
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_adapter, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.judulFilm.text = datafilm!![position].title
        holder.itemView.tanggalFilm.text = datafilm!![position].releaseDate
        holder.itemView.sutradaraFilm.text = datafilm!![position].director
        Glide.with(holder.itemView.context).load(datafilm!![position].image).apply(RequestOptions().override(120, 120)).into(holder.itemView.imageFilm)



        holder.itemView.cardFilm.setOnClickListener{
            onClick(datafilm!![position])
        }

    }

    override fun getItemCount(): Int {
        if (datafilm == null){
            return 0
        }else{
            return datafilm!!.size

        }
    }
}