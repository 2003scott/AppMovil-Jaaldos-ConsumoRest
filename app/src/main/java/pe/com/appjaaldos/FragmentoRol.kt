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
import pe.com.appjaaldos.adaptadores.AdaptadorRol
import pe.com.appjaaldos.clases.Rol
import pe.com.appjaaldos.databinding.FragmentoRolBinding
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.RolService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentoRol : Fragment() {
    //declaramos los controles
    private lateinit var txtCar:EditText
    private lateinit var chkEstRol:CheckBox
    private lateinit var lblCodRol: TextView
    private lateinit var btnRegistrarRol: Button
    private lateinit var btnActualizarRol: Button
    private lateinit var btnEliminarRol: Button
    private lateinit var lstRol: ListView

    //creamos un objeto de clase categoria
    private val objrol = Rol()

    //declaramos variable
    private var cod=0L
    private var fila=-1
    private var car=""
    private var est=false

    //declaramos el servicio
    private var rolService: RolService?=null

    //creamos un arraylist de Rol
    private var registrorol:List<Rol>?=null

    //creamos un obejto de la clase utilidad
    var objutilidad=Util()

    //creamos una transicion para el fragmento
    var ft: FragmentTransaction?=null

    private var dialogo: AlertDialog.Builder?=null

    private var _binding: FragmentoRolBinding?= null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val raiz = inflater.inflate(R.layout.fragmento_rol,container,false)
        //creamos los controles
        txtCar=raiz.findViewById(R.id.txtCar)
        chkEstRol=raiz.findViewById(R.id.chkEstRol)
        lblCodRol=raiz.findViewById(R.id.lblCodRol)
        btnRegistrarRol=raiz.findViewById(R.id.btnRegistrarRol)
        btnActualizarRol=raiz.findViewById(R.id.btnActualizarRol)
        btnEliminarRol=raiz.findViewById(R.id.btnEliminarRol)
        lstRol=raiz.findViewById(R.id.lstRol)

        //creamos el arraylist de Rol
        registrorol=ArrayList()

        //implementamos el servicio
        rolService=ApiUtil.rolService

        //mostramos los roles
        MostrarRol(raiz.context)

        //agregamos los eventos
        btnRegistrarRol.setOnClickListener{
            if (txtCar.getText().toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingese el cargo")
                txtCar.requestFocus()
            }else{
                //capturando los valores
                car=txtCar.getText().toString()
                est=if (chkEstRol.isChecked){
                    true
                }else{
                    false
                }
                //envieamos los valores a la clase
                objrol.cargo=car
                objrol.estado=est
                //lammamos al metodo para registrar
                RegistrarRol(raiz.context,objrol)
                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)
                //actualizamos el fragmento
                val frol=FragmentoRol()
                ft=fragmentManager?.beginTransaction()
                ft?.replace(R.id.contenedor,frol,null)
                ft?.addToBackStack(null)
                ft?.commit()
            }
        }

        lstRol.setOnItemClickListener(AdapterView.OnItemClickListener
        { parent, view, position, id ->
            fila=position
            //asignamos los valores a cada control
            lblCodRol.setText(""+(registrorol as ArrayList<Rol>).get(fila).codigo)
            txtCar.setText(""+(registrorol as ArrayList<Rol>).get(fila).cargo)
            if((registrorol as ArrayList<Rol>).get(fila).estado){
                chkEstRol.setChecked(true)
            }else{
                chkEstRol.setChecked(false)
            }
        }
        )

        btnActualizarRol.setOnClickListener {
            if(fila>=0){
                cod=lblCodRol.getText().toString().toLong()
                car=txtCar.getText().toString()
                est=if(chkEstRol.isChecked){
                    true
                }else{
                    false
                }

                objrol.codigo=cod
                objrol.cargo=car
                objrol.estado=est

                ActualizarRol(raiz.context,objrol,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)
                val frol= FragmentoRol()
                DialogoCRUD("Actualizacion de Rol","Se actualizo el rol",frol)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstRol.requestFocus()
            }
        }

        btnEliminarRol.setOnClickListener {
            if(fila>=0){
                cod=lblCodRol.getText().toString().toLong()

                objrol.codigo=cod

                EliminarRol(raiz.context,cod)

                objutilidad.Limpiar(raiz.findViewById<View>(R.id.frmRol) as ViewGroup)

                val frol= FragmentoRol()
                DialogoCRUD("Eliminacion de Rol", "Se elimino la categoria", frol)

            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista ")
            }
        }

        return raiz
    }


    fun MostrarRol(context: Context?){
        val call=rolService!!.MostrarRolPersonalizado()
        call!!.enqueue(object :Callback<List<Rol>?> {
            override fun onResponse(call: Call<List<Rol>?>, response: Response<List<Rol>?>) {
                if (response.isSuccessful){
                    registrorol=response.body()
                    lstRol.adapter=AdaptadorRol(context,registrorol)
                }
            }

            override fun onFailure(call: Call<List<Rol>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }

        })
    }

    //creamos una funcion para registrar rol
    fun RegistrarRol(context: Context?,r: Rol?){
        val call= rolService!!.RegistrarRol(r)
        call!!.enqueue(object :Callback<List<Rol>>{
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun ActualizarRol(context: Context?,r: Rol?,id:Long){
        val call= rolService!!.ActualizarRol(id,r)
        call!!.enqueue(object :Callback<List<Rol>>{
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    fun EliminarRol(context: Context?,id:Long){
        val call= rolService!!.EliminarRol(id)
        call!!.enqueue(object :Callback<List<Rol>>{
            override fun onResponse(call: Call<List<Rol>>, response: Response<List<Rol>>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<List<Rol>>, t: Throwable) {
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