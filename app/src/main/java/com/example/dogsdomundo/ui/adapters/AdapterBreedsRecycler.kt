package com.example.dogsdomundo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogsdomundo.R
import com.example.dogsdomundo.data.model.Dog
import java.util.Collections.sort

class AdapterBreedsRecycler(
    private var values: List<Dog>,
    private val context: Context
) : RecyclerView.Adapter<AdapterBreedsRecycler.ViewHolder>() {


    fun inflateDogClicked(listDog: List<Dog>) {
        values = listDog
        notifyDataSetChanged()
    }


    fun setValuesFromList(valuesFromList: List<Dog>) {
        sort(valuesFromList)
        values = valuesFromList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_dogs, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.breedsName.text = item.name
        imageRequisition(item.image, holder)
    }

    fun imageRequisition(url: String, holder: ViewHolder) {
        Glide.with(context)
            .load(url)
            .override(600, 700)
            .into(holder.breedsImage)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val breedsName: TextView = view.findViewById(R.id.breeds_name)
        val breedsImage: ImageView = view.findViewById(R.id.imageView)
        override fun toString(): String {
            return super.toString() + " '" + breedsName.text + "'"
        }
    }

}
