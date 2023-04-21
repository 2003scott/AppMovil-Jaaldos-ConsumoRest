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
import pe.com.appjaaldos.adaptadores.AdaptadorUbicacion
import pe.com.appjaaldos.clases.Ubicacion
import pe.com.appjaaldos.databinding.FragmentoUbicacionBinding
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.UbicacionService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentoUbicacion : Fragment() {

    // declaramos los controles
    private lateinit var txtPro:EditText
    private lateinit var txtDis:EditText
    private lateinit var chkEstubi: CheckBox
    private lateinit var lblCodUbi: TextView
    private lateinit var btnRegistrarUbi: Button
    private lateinit var btnActualizarubi: Button
    private lateinit var btnEliminarubi: Button
    private lateinit var lstUbi: ListView

   //creamos un objeto de clase ubicacion
    val objubicacion=Ubicacion()

    //declaramos variables
    private var cod=0L
    private var fila=-1
    private var pro=""
    private var dis=""
    private var est=false

    //declaramos el servicio
    private var ubicacionService:UbicacionService?=null

    //creamos un arraylist de Ubicacion
    private var registrooubicacion:List<Ubicacion>?=null

    //creamos un obejto de la clase utilidad
    var objutilidad= Util()

    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoUbicacionBinding?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz = inflater.inflate(R.layout.fragmento_ubicacion, container, false)
        //creamos los controles
        txtPro = raiz.findViewById(R.id.txtPro)
        txtDis = raiz.findViewById(R.id.txtDis)
        chkEstubi=raiz.findViewById(R.id.chkEstubi)
        lblCodUbi=raiz.findViewById(R.id.lblCodPro)
        btnRegistrarUbi=raiz.findViewById(R.id.btnRegitrarUbi)
        btnActualizarubi=raiz.findViewById(R.id.btnActualizarubi)
        btnEliminarubi=raiz.findViewById(R.id.btnEliminarubi)
        lstUbi=raiz.findViewById(R.id.lstUbi)

        // creamos el arraylist de Ubucacion
        registrooubicacion=ArrayList()

        //implementamos el servicio
        ubicacionService= ApiUtil.ubicacionService

        //mostramos las ubicaciones
        MostrarUbicacion(raiz.context)


        // agregamos los eventos
        btnRegistrarUbi.setOnClickListener {
            if (txtPro.getText().toString() == "") {
                objutilidad.MensajeToast(raiz.context, "Ingrese la provincia")
            } else if (txtDis.getText().toString() == "") {
                objutilidad.MensajeToast(raiz.context, "Ingrese el Distrito")
            } else {
                //capturando valores
                pro = txtPro.getText().toString()
                dis= txtDis.getText().toString()
                est = if (chkEstubi.isChecked) {
                    true
                } else {
                    false
                }
                //enviamos los valores a la clase
                objubicacion.provincia = pro
                objubicacion.distrito = dis
                objubicacion.estado = est
                //llamamos al metodo para registrar
                RegistrarUbicacion(raiz.context, objubicacion)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmUbicacion) as ViewGroup)
                //actualizamos el fragmento
                val fubicacion = FragmentoUbicacion()
                ft = fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor, fubicacion, null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }
        lstUbi.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodUbi.setText(""+(registrooubicacion as ArrayList<Ubicacion>).get(fila).codigo)
            txtPro.setText(""+(registrooubicacion as ArrayList<Ubicacion>).get(fila).provincia)
            txtDis.setText(""+(registrooubicacion as ArrayList<Ubicacion>).get(fila).distrito)
            if((registrooubicacion as ArrayList<Ubicacion>).get(fila).estado){
                chkEstubi.setChecked(true)
            }else{
                chkEstubi.setChecked(false)
            }
        }
        )

        btnActualizarubi.setOnClickListener {
            if(fila>=0){
                cod=lblCodUbi.getText().toString().toLong()
                pro=txtPro.getText().toString()
                dis=txtDis.getText().toString()
                est=if(chkEstubi.isChecked){
                    true
                }else{
                    false
                }
                objubicacion.codigo=cod
                objubicacion.provincia=pro
                objubicacion.distrito=dis
                objubicacion.estado=est

                ActualizarUbicacion(raiz.context,objubicacion,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmUbicacion) as ViewGroup)
                val fubicacion=FragmentoUbicacion()
                DialogoCRUD("Actualizacion de Ubicacion","Se actualizo la Ubicacion",fubicacion)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUbi.requestFocus()
            }
        }

        btnEliminarubi.setOnClickListener {
            if(fila>=0){
                cod=lblCodUbi.getText().toString().toLong()

                objubicacion.codigo=cod


                EliminarUbicacion(raiz.context,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmUbicacion) as ViewGroup)
                val fubicacion=FragmentoUbicacion()
                DialogoCRUD("Eliminacion de Ubicacion","Se elimino la ubicacion",fubicacion)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstUbi.requestFocus()
            }
        }

        return raiz
    }

    //creamos la funcion para mostrar ubicaciones
    fun MostrarUbicacion(context: Context?){
        val call= ubicacionService!!.MostrarUbicacionPersonalizado()
        call!!.enqueue(object : Callback<List<Ubicacion>?> {
            override fun onResponse(
                call: Call<List<Ubicacion>?>,
                response: Response<List<Ubicacion>?>
            ) {
                if(response.isSuccessful){
                    registrooubicacion=response.body()
                    lstUbi.adapter= AdaptadorUbicacion(context,registrooubicacion)
                }
            }
            override fun onFailure(call: Call<List<Ubicacion>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }

    fun RegistrarUbicacion(context: Context?,u: Ubicacion?){
        val call= ubicacionService!!.RegistrarUbicacion(u)
        call!!.enqueue(object :Callback<List<Ubicacion>>{
            override fun onResponse(call: Call<List<Ubicacion>>, response: Response<List<Ubicacion>>) {
                if(response.isSuccessful){
                    objutilidad.MensajeToast(context!!,"Se registro la ubicacion")
                }
            }

            override fun onFailure(call: Call<List<Ubicacion>>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }
        })
    }

    fun ActualizarUbicacion(context: Context?, r: Ubicacion?, id:Long){
        val call= ubicacionService!!.ActualizarUbicacion(id,r)
        call!!.enqueue(object :Callback<List<Ubicacion>>{
            override fun onResponse(call: Call<List<Ubicacion>>, response: Response<List<Ubicacion>>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<List<Ubicacion>>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarUbicacion(context: Context?,id:Long){
        val call= ubicacionService!!.EliminarUbicacion(id)
        call!!.enqueue(object :Callback<List<Ubicacion>>{
            override fun onResponse(call: Call<List<Ubicacion>>, response: Response<List<Ubicacion>>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<List<Ubicacion>>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun DialogoCRUD(titulo:String,mensaje:String,fragmento:Fragment){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Ok"){
                dialogo,which->
            ft=fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor,fragmento,null)
            ft?.addToBackStack(null)
            ft?.commit()
        }
        dialogo!!.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}