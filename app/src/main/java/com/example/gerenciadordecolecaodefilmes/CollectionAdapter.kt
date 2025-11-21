package com.example.gerenciadordecolecaodefilmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CollectionAdapter(
    private val collections: List<MovieCollection>,
    private val onItemClicked: (MovieCollection) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    // 1. ViewHolder: Encontra as Views
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewCollectionName)
    }

    // 2. Cria o ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return ViewHolder(view)
    }

    // 3. Liga os dados do objeto MovieCollection com as Views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collection = collections[position]

        // Referência a 'collection.name' agora funciona!
        holder.nameTextView.text = collection.name

        // Define o Listener de Clique
        holder.itemView.setOnClickListener {
            onItemClicked(collection)
        }
    }

    override fun getItemCount() = collections.size
}