package pe.com.appjaaldos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import pe.com.appjaaldos.adaptadores.AdaptadorCategoria
import pe.com.appjaaldos.clases.Categoria
import pe.com.appjaaldos.databinding.FragmentoCategoriaBinding
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.CategoriaService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentoBuscarCategoria.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarCategoria : Fragment() {
    //declaramos los controles
    private lateinit var txtBusCat: SearchView
    private lateinit var lstCat: ListView

    //cremamos un objeto de la clase categoria
    private val objcategoria= Categoria()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var nom=""
    private var est=false

    //llamamos al servicio
    private var categoriaService: CategoriaService?=null

    //creamos una lista de tipo categoria
    private var registrocategoria:List<Categoria>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

    //creams una variable para actualizar el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoCategoriaBinding? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_buscar_categoria,container,false)
        //creamos los controles
        txtBusCat=raiz.findViewById(R.id.txtBusCat)
        lstCat=raiz.findViewById(R.id.lstCat)

        //creamos el registro categoria
        registrocategoria=ArrayList()
        //implementamos el servicio
        categoriaService= ApiUtil.categoriaService
        //mostramos las categorias
        MostrarCategoria(raiz.context)

        txtBusCat.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstCat.adapter as AdaptadorCategoria).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }

    fun MostrarCategoria(context: Context?){
        val call= categoriaService!!.MostrarCategoria()
        call!!.enqueue(object : Callback<List<Categoria>?> {
            override fun onResponse(
                call: Call<List<Categoria>?>,
                response: Response<List<Categoria>?>
            ) {
                if(response.isSuccessful){
                    registrocategoria=response.body()
                    lstCat.adapter= AdaptadorCategoria(context,registrocategoria)


                }
            }

            override fun onFailure(call: Call<List<Categoria>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })

    }
}
