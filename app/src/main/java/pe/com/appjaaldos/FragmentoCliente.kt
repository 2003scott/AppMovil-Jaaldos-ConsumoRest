package pe.com.appjaaldos

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import pe.com.appjaaldos.adaptadores.AdaptadorCliente
import pe.com.appjaaldos.adaptadores.AdaptadorComboUbicacion
import pe.com.appjaaldos.clases.Cliente
import pe.com.appjaaldos.clases.Ubicacion
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.ClienteService
import pe.com.appjaaldos.servicios.UbicacionService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentoCliente : Fragment() {

    //declaramos los controles
    private lateinit var txtNombreCli: EditText
    private lateinit var txtApellidoCli: EditText
    private lateinit var txtDniCli: EditText
    private lateinit var txtTelefonoCli: EditText
    private lateinit var txtCorreoCli: EditText
    private lateinit var cboUbicacionCli: Spinner
    private lateinit var txtDirrecionCli: EditText
    private lateinit var chkEstCli: CheckBox
    private lateinit var lblCodCli: TextView

    private lateinit var btnRegistrarCli: Button
    private lateinit var btnActualizarCli: Button
    private lateinit var btnEliminarCli: Button
    private lateinit var lstCli: ListView

    //cremamos un objeto de la clase
    private val objubicacion = Ubicacion()
    private val objcliente = Cliente()

    //creamos variables
    private var cod = 0L
    private var nom = ""
    private var ape = ""
    private var dni = ""
    private var tel = ""
    private var cor = ""
    private var codubi = 0L
    private var nomubi = ""
    private var dir = ""
    private var est = false
    private var fila = -1
    private var indice = -1
    private var pos = -1

    private var dialogo: AlertDialog.Builder? = null
    private var ft: FragmentTransaction? = null

    //llamamos al servicio
    private var ubicacionservice: UbicacionService? = null
    private var clienteservice: ClienteService? = null

    //creamos una lista de tipo
    private var registroubicacion: List<Ubicacion>? = null
    private var registrocliente: List<Cliente>? = null

    //creamos un objeto de la clase Util
    private val objutilidad = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val raiz = inflater.inflate(R.layout.fragmento_cliente, container, false)

        //creamos los controles
        txtNombreCli=raiz.findViewById(R.id.txtNombreCli)
        cboUbicacionCli=raiz.findViewById(R.id.cboUbicacionCli)
        txtApellidoCli=raiz.findViewById(R.id.txtApellidoCli)
        txtDniCli=raiz.findViewById(R.id.txtDniCli)
        txtTelefonoCli=raiz.findViewById(R.id.txtTelefonoCli)
        txtCorreoCli=raiz.findViewById(R.id.txtCorreoCli)
        txtDirrecionCli=raiz.findViewById(R.id.txtDirrecionCli)
        lblCodCli=raiz.findViewById(R.id.lblCodCli)
        chkEstCli=raiz.findViewById(R.id.chkEstCli)
        btnRegistrarCli=raiz.findViewById(R.id.btnRegistrarCli)
        btnActualizarCli=raiz.findViewById(R.id.btnActualizarCliente)
        btnEliminarCli=raiz.findViewById(R.id.btnEliminarCli)
        lstCli=raiz.findViewById(R.id.lstCli)

        //creamos el registro
        registroubicacion=ArrayList()
        registrocliente=ArrayList()
        //implementamos el servicio
        ubicacionservice= ApiUtil.ubicacionService
        clienteservice= ApiUtil.clienteService

        //cargamos el combo
        MostrarComboUbicacion(raiz.context)
        MostrarCliente(raiz.context)

        //llamamos a los eventos
        btnRegistrarCli.setOnClickListener {
            if(txtNombreCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el nombre")
                txtNombreCli.requestFocus()
            }else if(txtApellidoCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el apellido")
                txtApellidoCli.requestFocus()
            }else if(txtDniCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la dni")
                txtDniCli.requestFocus()
            }else if(txtTelefonoCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la telefono")
                txtTelefonoCli.requestFocus()
            }else if(txtCorreoCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el correo")
                txtCorreoCli.requestFocus()
            }else if(txtDirrecionCli.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la direccion")
                txtDirrecionCli.requestFocus()

            }else if(cboUbicacionCli.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una ubicacion")
                cboUbicacionCli.requestFocus()
            }else{
                //capturando valores
                nom=txtNombreCli.text.toString()
                ape=txtApellidoCli.text.toString()
                dni=txtDniCli.text.toString()
                tel=txtTelefonoCli.text.toString()
                cor=txtCorreoCli.text.toString()
                dir=txtDirrecionCli.text.toString()
                pos=cboUbicacionCli.selectedItemPosition
                codubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).codigo
                nomubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).distrito.toString()
                est=if(chkEstCli.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objcliente.nombre=nom
                objcliente.apellidos=ape
                objcliente.dni=dni
                objcliente.telefono=tel
                objcliente.correo=cor
                objcliente.direccion=dir


                objubicacion.codigo=codubi
                objcliente.ubicacion=objubicacion

                objcliente.estado=est

                //llamamos a la funcion para registrar
                RegistrarCliente(raiz.context,objcliente)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Registro de cliente","Se registro el cliente correctamente",fcliente)
            }
        }

        lstCli.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).codigo)
            txtNombreCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).nombre)
            txtApellidoCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).apellidos)
            txtTelefonoCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).telefono)
            txtDniCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).dni)
            txtCorreoCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).correo)
            txtDirrecionCli.setText(""+ (registrocliente as ArrayList<Cliente>).get(fila).direccion)
            for(x in (registroubicacion as ArrayList<Ubicacion>).indices){
                if((registroubicacion as ArrayList<Ubicacion>).get(x).distrito== (registrocliente as ArrayList<Cliente>).get(fila).ubicacion?.distrito){
                    indice=x
                }
            }
            cboUbicacionCli.setSelection(indice)
            if((registrocliente as ArrayList<Cliente>).get(fila).estado){
                chkEstCli.setChecked(true)
            }else{
                chkEstCli.setChecked(false)
            }
        }

        btnActualizarCli.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodCli.text.toString().toLong()
                nom=txtNombreCli.text.toString()
                ape=txtApellidoCli.text.toString()
                dni=txtDniCli.text.toString()
                tel=txtTelefonoCli.text.toString()
                cor=txtCorreoCli.text.toString()
                dir=txtDirrecionCli.text.toString()
                pos=cboUbicacionCli.selectedItemPosition
                codubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).codigo
                nomubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).distrito.toString()
                est=if(chkEstCli.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objcliente.nombre=nom
                objcliente.apellidos=ape
                objcliente.dni=dni
                objcliente.telefono=tel
                objcliente.correo=cor
                objcliente.direccion=dir


                objubicacion.codigo=codubi
                objcliente.ubicacion=objubicacion

                objcliente.estado=est

                //llamamos a la funcion para registrar
                ActualizarCliente(raiz.context,objcliente,cod)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Actualizacion de cliente","Se actualizo el cliente correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
            }

        }

        btnEliminarCli.setOnClickListener {
            if(fila>=0){
                cod=lblCodCli.text.toString().toLong()
                nom=txtNombreCli.text.toString()


                //llamamos a la funcion para registrar
                EliminarCliente(raiz.context,cod)
                val fcliente=FragmentoCliente()
                DialogoCRUD("Eliminacion de cliente","Se elimino el cliente correctamente",fcliente)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstCli.requestFocus()
            }
        }

        return raiz;

    }

    //creamos una funcion para mostrar el combo de la
    fun MostrarComboUbicacion(context: Context?){
        val call= ubicacionservice!!.MostrarUbicacionPersonalizado()
        call!!.enqueue(object : Callback<List<Ubicacion>?> {
            override fun onResponse(
                call: Call<List<Ubicacion>?>,
                response: Response<List<Ubicacion>?>
            ) {
                if(response.isSuccessful){
                    registroubicacion=response.body()
                    cboUbicacionCli.adapter= AdaptadorComboUbicacion(context,registroubicacion)


                }
            }

            override fun onFailure(call: Call<List<Ubicacion>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los
    fun MostrarCliente(context: Context?){
        val call= clienteservice!!.MostrarClientePersonalizado()
        call!!.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>,
                response: Response<List<Cliente>?>
            ) {
                if(response.isSuccessful){
                    registrocliente=response.body()
                    lstCli.adapter= AdaptadorCliente(context,registrocliente)
                }
            }

            override fun onFailure(call: Call<List<Cliente>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarCliente(context: Context?, cli: Cliente?){
        val call= clienteservice!!.RegistrarCliente(cli)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarCliente(context: Context?, cli: Cliente?, id:Long){
        val call= clienteservice!!.ActualizarCliente(id,cli)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarCliente(context: Context?, id:Long){
        val call= clienteservice!!.EliminarCliente(id)
        call!!.enqueue(object : Callback<Cliente?> {
            override fun onResponse(call: Call<Cliente?>, response: Response<Cliente?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Cliente?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para los cuadros de dialogo del CRUD
    fun DialogoCRUD(titulo: String, mensaje: String, fragmento: Fragment) {
        dialogo = AlertDialog.Builder(context)
        dialogo!!.setTitle(titulo)
        dialogo!!.setMessage(mensaje)
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Ok") { dialogo, which ->
            ft = fragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor, fragmento, null)
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

    }
}