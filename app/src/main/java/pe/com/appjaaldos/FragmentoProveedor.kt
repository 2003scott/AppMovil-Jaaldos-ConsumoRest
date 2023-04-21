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
import pe.com.appjaaldos.adaptadores.AdaptadorComboUbicacion
import pe.com.appjaaldos.adaptadores.AdaptadorProveedor
import pe.com.appjaaldos.clases.Proveedor
import pe.com.appjaaldos.clases.Ubicacion
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.ProveedorService
import pe.com.appjaaldos.servicios.UbicacionService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoProveedor : Fragment() {


    //declaramos los controles
    private lateinit var txtNombreProv: EditText
    private lateinit var txtTelefonoProv: EditText
    private lateinit var txtCorreoProv: EditText
    private lateinit var cboUbicacionProv: Spinner
    private lateinit var txtDirrecionProv: EditText
    private lateinit var chkEstProv: CheckBox
    private lateinit var lblCodProv: TextView

    private lateinit var btnRegistrarProv: Button
    private lateinit var btnActualizarProv: Button
    private lateinit var btnEliminarProv: Button
    private lateinit var lstProv: ListView

    //cremamos un objeto de la clase categoria
    private val objubicacion = Ubicacion()
    private val objproveedor = Proveedor()

    //creamos variables
    private var cod = 0L
    private var nom = ""
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
    private var proveedorservice: ProveedorService? = null

    //creamos una lista de tipo categoria
    private var registroubicacion: List<Ubicacion>? = null
    private var registroproveedor: List<Proveedor>? = null

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
        val raiz = inflater.inflate(R.layout.fragmento_proveedor, container, false)

        //creamos los controles
        txtNombreProv=raiz.findViewById(R.id.txtNomProv)
        cboUbicacionProv=raiz.findViewById(R.id.cboUbicacionProv)
        txtTelefonoProv=raiz.findViewById(R.id.txtTelProv)
        txtCorreoProv=raiz.findViewById(R.id.txtCorreoProv)
        txtDirrecionProv=raiz.findViewById(R.id.txtDirProv)
        lblCodProv=raiz.findViewById(R.id.lblCodProv)
        chkEstProv=raiz.findViewById(R.id.chkEstProv)
        btnRegistrarProv=raiz.findViewById(R.id.btnRegistrarProv)
        btnActualizarProv=raiz.findViewById(R.id.btnActualizarProv)
        btnEliminarProv=raiz.findViewById(R.id.btnEliminarProv)
        lstProv=raiz.findViewById(R.id.lstProv)

        //creamos el registro categoria
        registroubicacion=ArrayList()
        registroproveedor=ArrayList()
        //implementamos el servicio
        ubicacionservice= ApiUtil.ubicacionService
        proveedorservice= ApiUtil.proveedorService

        //cargamos el combo categoria
        MostrarComboUbicacion(raiz.context)
        MostrarProveedor(raiz.context)


        btnRegistrarProv.setOnClickListener {
            if(txtNombreProv.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el nombre")
                txtNombreProv.requestFocus()
            }else if(txtTelefonoProv.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la telefono")
                txtTelefonoProv.requestFocus()
            }else if(txtCorreoProv.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el correo")
                txtCorreoProv.requestFocus()
            }else if(txtDirrecionProv.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la direccion")
                txtDirrecionProv.requestFocus()

            }else if(cboUbicacionProv.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una ubicacion")
                cboUbicacionProv.requestFocus()
            }else{
                //capturando valores
                nom=txtNombreProv.text.toString()
                tel=txtTelefonoProv.text.toString()
                cor=txtCorreoProv.text.toString()
                dir=txtDirrecionProv.text.toString()
                pos=cboUbicacionProv.selectedItemPosition
                codubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).codigo
                nomubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).distrito.toString()
                est=if(chkEstProv.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objproveedor.nombre=nom
                objproveedor.telefono=tel
                objproveedor.correo=cor
                objproveedor.direccion=dir


                objubicacion.codigo=codubi
                objproveedor.ubicacion=objubicacion

                objproveedor.estado=est

                //llamamos a la funcion para registrar
                RegistrarProveedor(raiz.context,objproveedor)
                val fproveedor=FragmentoProveedor()
                DialogoCRUD("Registro de proveedor","Se registro el proveedor correctamente",fproveedor)
            }

        }



        lstProv.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodProv.setText(""+ (registroproveedor as ArrayList<Proveedor>).get(fila).codigo)
            txtNombreProv.setText(""+ (registroproveedor as ArrayList<Proveedor>).get(fila).nombre)
            txtTelefonoProv.setText(""+ (registroproveedor as ArrayList<Proveedor>).get(fila).telefono)
            txtCorreoProv.setText(""+ (registroproveedor as ArrayList<Proveedor>).get(fila).correo)
            txtDirrecionProv.setText(""+ (registroproveedor as ArrayList<Proveedor>).get(fila).direccion)
            for(x in (registroubicacion as ArrayList<Ubicacion>).indices){
                if((registroubicacion as ArrayList<Ubicacion>).get(x).distrito== (registroproveedor as ArrayList<Proveedor>).get(fila).ubicacion?.distrito){
                    indice=x
                }
            }
            cboUbicacionProv.setSelection(indice)
            if((registroproveedor as ArrayList<Proveedor>).get(fila).estado){
                chkEstProv.setChecked(true)
            }else{
                chkEstProv.setChecked(false)
            }
        }

        btnActualizarProv.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodProv.text.toString().toLong()
                nom=txtNombreProv.text.toString()
                tel=txtTelefonoProv.text.toString()
                cor=txtCorreoProv.text.toString()
                dir=txtDirrecionProv.text.toString()
                pos=cboUbicacionProv.selectedItemPosition
                codubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).codigo
                nomubi= (registroubicacion as ArrayList<Ubicacion>).get(pos).distrito.toString()
                est=if(chkEstProv.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objproveedor.nombre=nom
                objproveedor.telefono=tel
                objproveedor.correo=cor
                objproveedor.direccion=dir


                objubicacion.codigo=codubi
                objproveedor.ubicacion=objubicacion

                objproveedor.estado=est

                //llamamos a la funcion para registrar
                ActualizarProveedor(raiz.context,objproveedor,cod)
                val fproveedor=FragmentoProveedor()
                DialogoCRUD("Actualizacion de proveedor","Se actualizo el proveedor correctamente",fproveedor)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }

        }



        btnEliminarProv.setOnClickListener {
            if(fila>=0){
                cod=lblCodProv.text.toString().toLong()
                nom=txtNombreProv.text.toString()


                //llamamos a la funcion para registrar
                EliminarProveedor(raiz.context,cod)
                val fproveedore=FragmentoProveedor()
                DialogoCRUD("Eliminacion de proveedor","Se elimino el proveedor correctamente",fproveedore)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstProv.requestFocus()
            }
        }

        return raiz;

    }

    //creamos una funcion para mostrar el combo de la categoria
    fun MostrarComboUbicacion(context: Context?){
        val call= ubicacionservice!!.MostrarUbicacionPersonalizado()
        call!!.enqueue(object : Callback<List<Ubicacion>?> {
            override fun onResponse(
                call: Call<List<Ubicacion>?>,
                response: Response<List<Ubicacion>?>
            ) {
                if(response.isSuccessful){
                    registroubicacion=response.body()
                    cboUbicacionProv.adapter= AdaptadorComboUbicacion(context,registroubicacion)


                }
            }

            override fun onFailure(call: Call<List<Ubicacion>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los producto
    fun MostrarProveedor(context: Context?){
        val call= proveedorservice!!.MostrarProveedorPersonalizado()
        call!!.enqueue(object : Callback<List<Proveedor>?> {
            override fun onResponse(
                call: Call<List<Proveedor>?>,
                response: Response<List<Proveedor>?>
            ) {
                if(response.isSuccessful){
                    registroproveedor=response.body()
                    lstProv.adapter= AdaptadorProveedor(context,registroproveedor)
                }
            }

            override fun onFailure(call: Call<List<Proveedor>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para registrar
    fun RegistrarProveedor(context: Context?, prov: Proveedor?){
        val call= proveedorservice!!.RegistrarProveedor(prov)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar
    fun ActualizarProveedor(context: Context?, prov: Proveedor?, id:Long){
        val call= proveedorservice!!.ActualizarProveedor(id,prov)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarProveedor(context: Context?, id:Long){
        val call= proveedorservice!!.EliminarProveedor(id)
        call!!.enqueue(object : Callback<Proveedor?> {
            override fun onResponse(call: Call<Proveedor?>, response: Response<Proveedor?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Proveedor?>, t: Throwable) {
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