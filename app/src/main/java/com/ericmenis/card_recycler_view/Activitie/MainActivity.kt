package com.ericmenis.card_recycler_view.Activitie

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ericmenis.card_recycler_view.Adapter.MyAdapter
import com.ericmenis.card_recycler_view.Model.Personaje
import com.ericmenis.card_recycler_view.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val personadbz = ArrayList<Personaje>()
    val display = ArrayList<Personaje>()
    lateinit var myAdapter: MyAdapter
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personadbz.add(Personaje("Goku", R.mipmap.ic_goku, "Es el protagonista principal del manga y anime de Dragon Ball creado por Akira Toriyama. Su nombre real y de nacimiento es Kacarrot."))
        personadbz.add(Personaje("Gohan", R.mipmap.ic_gohan, "Es un mestizo entre saiyano y humano terrícola. Es el primer hijo de Son Goku y Chi-Chi, hermano mayor de Son Goten, esposo de Videl y padre de Pan."))
        personadbz.add(Personaje("Vegeta", R.mipmap.ic_vegeta, "Es el hijo mayor del Rey Vegeta, así como el príncipe más reciente de la Familia real saiyana. " +
                                                                                    "Es el eterno rival de Son Goku, hermano mayor de Tarble, el esposo de Bulma, padre de Trunks y Bra y ancestro de Vegeta Jr."))
        personadbz.add(Personaje("Piccolo", R.mipmap.ic_piccolo, "Es un namekiano que surgió tras ser creado en los últimos momentos de vida de su padre, siendo su actual reencarnación. Aunque en un principio fue el archienemigo de Son Goku, con el paso del tiempo fue haciéndose menos malvado hasta finalmente convertirse en un ser bondadoso y miembro de los Guerreros Z. A través del tiempo, también comenzó a tomarle cariño a su discípulo Son Gohan, a quien veía como una especie de \"vástago\" y formando un lazo de amistad con este."))
        personadbz.add(Personaje("Trunks", R.mipmap.ic_trunks, "Es un mestizo entre humano terrícola y Saiyan nacido en la Tierra, e hijo de Bulma y Vegeta."))
        display.addAll(personadbz)

        myAdapter = MyAdapter(display, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

        preferences = getSharedPreferences("My_Pref", Context.MODE_PRIVATE)
        val mSortSetting =  preferences.getString("Sort", "Ascending")

        if (mSortSetting == "Ascending"){

        } else if (mSortSetting == "Descending"){
            SortDescending(myAdapter)
        }
    }

    private fun SortDescending(myAdapter: MyAdapter) {
        display.sortWith(compareBy{it.titledbz})
        display.reverse()
        myAdapter.notifyDataSetChanged()
    }

    private fun SortAscending(myAdapter: MyAdapter) {
        display.sortWith(compareBy{it.titledbz})
        myAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null){

            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()){
                        display.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        personadbz.forEach {
                            if (it.titledbz.toLowerCase(Locale.getDefault()).contains(search)){
                                display.add(it)
                            }
                        }
                        recyclerView.adapter?.notifyDataSetChanged()

                    }

                    else{
                        display.clear()
                        display.addAll(personadbz)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    return true
                }

            })

        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if(id == R.id.sorting){
            sortDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortDialog() {
        val options = arrayOf("Ascendente", "Descendente")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort By")
        builder.setIcon(R.drawable.ic_action_sort)

        builder.setItems(options){ dialog, which ->
            if (which == 0){
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", "Ascending")
                editor.apply()
                SortAscending(myAdapter)
                Toast.makeText(this@MainActivity, "Orden Ascendente", Toast.LENGTH_SHORT).show()
            }
            if (which == 1){
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", "Descending")
                editor.apply()
                SortDescending(myAdapter)
                Toast.makeText(this@MainActivity, "Orden Descendiente", Toast.LENGTH_SHORT).show()
            }
        }
        builder.create().show()
    }
}
