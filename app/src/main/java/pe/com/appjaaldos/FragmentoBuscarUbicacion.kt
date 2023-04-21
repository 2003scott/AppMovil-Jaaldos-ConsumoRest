package pe.com.appjaaldos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import pe.com.appjaaldos.adaptadores.AdaptadorUbicacion
import pe.com.appjaaldos.clases.Ubicacion
import pe.com.appjaaldos.databinding.FragmentoUbicacionBinding
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.UbicacionService
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
 * Use the [FragmentoBuscarUbicacion.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentoBuscarUbicacion : Fragment() {
    //declaramos los controles
    private lateinit var txtBusUbi :SearchView
    private lateinit var lstUbi: ListView

    //cremamos un objeto de la clase ubicacion
    private val objubicacion = Ubicacion()

    //creamos variables
    private var cod=0L
    private var fila=-1
    private var pro=""
    private var dis=""
    private var est=false

    //llamamos al servicio
    private var ubicacionService : UbicacionService?=null

    //creamos una lista de tipo ubicacion
    private var registroubicacion:List<Ubicacion>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

    //creams una variable para actualizar el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoUbicacionBinding? = null
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
        // Inflate the layout for this fragment
        val raiz=inflater.inflate(R.layout.fragmento_buscar_ubicacion,container,false)
        //creamos los controles
        txtBusUbi=raiz.findViewById(R.id.txtBusUbi)
        lstUbi=raiz.findViewById(R.id.lstUbi)

        //creamos el registro ubicacion
        registroubicacion=ArrayList()
        //implementamos el servicio
        ubicacionService= ApiUtil.ubicacionService
        //mostramos las ubicacion
        MostrarUbicacion(raiz.context)

        txtBusUbi.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (lstUbi.adapter as AdaptadorUbicacion).filter(newText ?: "")
                return true
            }
        })

        return raiz
    }


    fun MostrarUbicacion(context: Context?){
        val call= ubicacionService!!.MostrarUbicacion()
        call!!.enqueue(object : Callback<List<Ubicacion>?> {
            override fun onResponse(
                call: Call<List<Ubicacion>?>,
                response: Response<List<Ubicacion>?>
            ) {
                if(response.isSuccessful){
                    registroubicacion=response.body()
                    lstUbi.adapter= AdaptadorUbicacion(context,registroubicacion)
                }
            }
            override fun onFailure(call: Call<List<Ubicacion>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentoBuscarUbicacion.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentoBuscarUbicacion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}