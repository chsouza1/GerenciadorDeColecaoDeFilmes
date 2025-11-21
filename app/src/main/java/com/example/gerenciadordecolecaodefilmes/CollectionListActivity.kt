package com.example.gerenciadordecolecaodefilmes // Substitua pelo seu pacote real!

import CollectionDao
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CollectionListActivity : AppCompatActivity() {

    // Componentes e lógica
    private lateinit var dao: CollectionDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var collectionAdapter: CollectionAdapter
    private var collectionList: MutableList<MovieCollection> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_list)

        // 1. Inicializa o DAO
        dao = CollectionDao(this)

        // 2. Configura o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCollections)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Configura o Adapter com o Listener de clique
        collectionAdapter = CollectionAdapter(collectionList) { collection ->
            // Lógica de clique no item da lista (Vai para a Activity 2)
            navigateToMovieList(collection.id)
        }
        recyclerView.adapter = collectionAdapter

        // 4. Configura o FAB para adicionar nova coleção
        val fabAddCollection = findViewById<FloatingActionButton>(R.id.fabAddCollection)
        fabAddCollection.setOnClickListener {
            // Aqui você deve iniciar uma nova Activity ou um Dialog para adicionar uma coleção
            // Por simplicidade, vamos usar um Toast ou uma função de teste por enquanto,
            // mas o ideal é criar uma 4ª Activity de cadastro de coleção ou usar um AlertDialog.
            addTestCollection() // Função de teste para popular o banco inicialmente
        }
    }

    override fun onResume() {
        super.onResume()
        // Sempre que a Activity volta ao foco, recarrega a lista
        loadCollections()
    }

    /**
     * Carrega as coleções do SQLite e atualiza o RecyclerView.
     */
    private fun loadCollections() {
        collectionList.clear()
        // Chama o método de leitura do DAO
        val fetchedCollections = dao.getAllCollections()
        collectionList.addAll(fetchedCollections)

        // Notifica o adapter que os dados mudaram para redesenhar a lista
        collectionAdapter.notifyDataSetChanged()
    }

    /**
     * Simula a adição de uma nova coleção. (A ser substituído por uma tela de cadastro)
     */
    private fun addTestCollection() {
        val newCollection = MovieCollection(name = "Minha Primeira Coleção")
        dao.insertCollection(newCollection)
        loadCollections() // Recarrega a lista para mostrar a nova coleção
    }

    /**
     * Navega para a Activity 2 (MovieListActivity), passando o ID da coleção.
     */
    private fun navigateToMovieList(collectionId: Long) {
        val intent = Intent(this, MovieListActivity::class.java).apply {
            // Chave-Valor para enviar o ID via Intent
            putExtra("COLLECTION_ID_EXTRA", collectionId)
        }
        startActivity(intent)
    }
}