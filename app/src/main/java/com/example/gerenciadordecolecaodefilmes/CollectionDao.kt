import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.gerenciadordecolecaodefilmes.DatabaseHelper
import com.example.gerenciadordecolecaodefilmes.Movie
import com.example.gerenciadordecolecaodefilmes.MovieCollection

class CollectionDao(context: Context) {


    private val dbHelper = DatabaseHelper(context)


    private val TABLE_MOVIES = DatabaseHelper.TABLE_MOVIES
    private val COL_M_TITLE = DatabaseHelper.COL_M_TITLE
    private val COL_M_YEAR = DatabaseHelper.COL_M_YEAR
    private val COL_M_GENRE = DatabaseHelper.COL_M_GENRE
    private val COL_M_COLLECTION_ID = DatabaseHelper.COL_M_COLLECTION_ID


    private val TABLE_COLLECTIONS = DatabaseHelper.TABLE_COLLECTIONS
    private val COL_C_NAME = DatabaseHelper.COL_C_NAME



    /**
     * Insere uma nova Coleção no banco de dados.
     * @return O ID da nova linha inserida, ou -1 se a inserção falhar.
     */
    fun insertCollection(collection: MovieCollection): Long {

        val db = dbHelper.writableDatabase


        val values = ContentValues().apply {
            put(COL_C_NAME, collection.name)
        }

        val newRowId = db.insert(TABLE_COLLECTIONS, null, values)

        db.close()

        return newRowId
    }

    /**
     * Insere um novo Filme no banco de dados.
     * @return O ID da nova linha inserida, ou -1 se a inserção falhar.
     */
    fun insertMovie(movie: Movie): Long {

        val db = dbHelper.writableDatabase


        val values = ContentValues().apply {
            put(COL_M_TITLE, movie.title)
            put(COL_M_YEAR, movie.year)
            put(COL_M_GENRE, movie.genre)
            put(COL_M_COLLECTION_ID, movie.collectionId) // FK
        }


        val newRowId = db.insert(TABLE_MOVIES, null, values)

        db.close()

        return newRowId
    }


    /**
     * Retorna todas as Coleções do banco de dados.
     */
    fun getAllCollections(): List<MovieCollection> {
        val collections = mutableListOf<MovieCollection>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $TABLE_COLLECTIONS", null)

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseHelper.COL_C_ID))
                val name = getString(getColumnIndexOrThrow(COL_C_NAME))

                collections.add(MovieCollection(id, name))
            }
        }
        cursor.close()
        db.close()
        return collections
    }

    /**
     * Retorna todos os Filmes que pertencem a uma Coleção específica.
     */
    fun getMoviesByCollectionId(collectionId: Long): List<Movie> {
        val movies = mutableListOf<Movie>()
        val db = dbHelper.readableDatabase

        val selection = "$COL_M_COLLECTION_ID = ?" // Cláusula WHERE
        val selectionArgs = arrayOf(collectionId.toString()) // Argumento da cláusula WHERE

        val cursor: Cursor = db.query(
            TABLE_MOVIES,  // A tabela para consultar
            null,          // Colunas para retornar (null = todas)
            selection,     // As colunas para a cláusula WHERE
            selectionArgs, // Os valores para a cláusula WHERE
            null,          // Não agrupar as linhas
            null,          // Não filtrar por grupos de linhas
            null           // A ordem de classificação
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseHelper.COL_M_ID))
                val title = getString(getColumnIndexOrThrow(COL_M_TITLE))
                val year = getInt(getColumnIndexOrThrow(COL_M_YEAR))
                val genre = getString(getColumnIndexOrThrow(COL_M_GENRE))
                val movieId = getLong(getColumnIndexOrThrow(COL_M_COLLECTION_ID))

                movies.add(Movie(id, title, year, genre, movieId))
            }
        }
        cursor.close()
        db.close()
        return movies
    }
}