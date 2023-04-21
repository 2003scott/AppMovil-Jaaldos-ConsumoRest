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
import pe.com.appjaaldos.adaptadores.AdaptadorComboCategoria
import pe.com.appjaaldos.adaptadores.AdaptadorProducto
import pe.com.appjaaldos.clases.Categoria
import pe.com.appjaaldos.clases.Producto
import pe.com.appjaaldos.remoto.ApiUtil
import pe.com.appjaaldos.servicios.CategoriaService
import pe.com.appjaaldos.servicios.ProductoService
import pe.com.appjaaldos.utilidad.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentoProducto : Fragment() {

    //declaramos los controles
    private lateinit var txtNomPro: EditText
    private lateinit var cboCat: Spinner
    private lateinit var txtStock: EditText
    private lateinit var txtPrec: EditText
    private lateinit var chkEstPro: CheckBox
    private lateinit var lblCodPro: TextView
    private lateinit var btnRegistrarPro: Button
    private lateinit var btnActualizarPro: Button
    private lateinit var btnEliminarPro: Button
    private lateinit var lstPro: ListView

    //cremamos un objeto de la clase categoria
    private val objcategoria= Categoria()
    private val objproducto=Producto()

    //creamos variables
    private var cod=0L
    private var nom=""
    private var prec=0.0
    private var stock=0
    private var codcat=0L
    private var nomcat=""
    private var est=false
    private var fila=-1
    private var indice=-1
    private var pos=-1

    private var dialogo: AlertDialog.Builder?=null
    private var ft: FragmentTransaction?=null

    //llamamos al servicio
    private var categoriaService: CategoriaService?=null
    private var productoService: ProductoService?=null

    //creamos una lista de tipo categoria
    private var registrocategoria:List<Categoria>?=null
    private var registroproducto:List<Producto>?=null

    //creamos un objeto de la clase Util
    private val objutilidad= Util()

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
        val raiz=inflater.inflate(R.layout.fragmento_producto, container, false)

        //creamos los controles
        txtNomPro=raiz.findViewById(R.id.txtNomPro)
        cboCat=raiz.findViewById(R.id.cboCat)
        txtStock=raiz.findViewById(R.id.txtStock)
        txtPrec=raiz.findViewById(R.id.txtPrecio)
        lblCodPro=raiz.findViewById(R.id.lblCodPro)
        chkEstPro=raiz.findViewById(R.id.chkEstPro)
        btnRegistrarPro=raiz.findViewById(R.id.btnRegistrarPro)
        btnActualizarPro=raiz.findViewById(R.id.btnActualizarPro)
        btnEliminarPro=raiz.findViewById(R.id.btnEliminarPro)
        lstPro=raiz.findViewById(R.id.lstPro)

        //creamos el registro categoria
        registrocategoria=ArrayList()
        registroproducto=ArrayList()
        //implementamos el servicio
        categoriaService= ApiUtil.categoriaService
        productoService= ApiUtil.productoService

        //cargamos el combo categoria
        MostrarComboCategoria(raiz.context)
        MostrarProducto(raiz.context)

        //llamamos a los eventos
        btnRegistrarPro.setOnClickListener {
            if(txtNomPro.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el nombre")
                txtNomPro.requestFocus()
            }else if(txtStock.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa el Stock")
                txtStock.requestFocus()
            }else if(txtPrec.text.toString()==""){
                objutilidad.MensajeToast(raiz.context,"Ingresa la cantidad")
                txtPrec.requestFocus()
            }else if(cboCat.selectedItemPosition==-1){
                objutilidad.MensajeToast(raiz.context,"Seleccione una categoria")
                cboCat.requestFocus()
            }else{
                //capturando valores
                nom=txtNomPro.text.toString()
                prec=txtPrec.text.toString().toDouble()
                stock=txtStock.text.toString().toInt()
                pos=cboCat.selectedItemPosition
                codcat= (registrocategoria as ArrayList<Categoria>).get(pos).codigo
                nomcat= (registrocategoria as ArrayList<Categoria>).get(pos).nombre.toString()
                est=if(chkEstPro.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objproducto.nombre=nom
                objproducto.preciosoles=prec
                objproducto.stock=stock

                objcategoria.codigo=codcat
                objproducto.categoria=objcategoria

                objproducto.estado=est

                //llamamos a la funcion para registrar
                RegistrarProducto(raiz.context,objproducto)
                val fproducto=FragmentoProducto()
                DialogoCRUD("Registro de Producto","Se registro el producto correctamente",fproducto)
            }
        }

        lstPro.setOnItemClickListener { adapterView, view, i, l ->
            fila=i
            //asignamos los valores a los controles
            lblCodPro.setText(""+ (registroproducto as ArrayList<Producto>).get(fila).codigo)
            txtNomPro.setText(""+ (registroproducto as ArrayList<Producto>).get(fila).nombre)
            txtStock.setText(""+ (registroproducto as ArrayList<Producto>).get(fila).stock)
            txtPrec.setText(""+ (registroproducto as ArrayList<Producto>).get(fila).preciosoles)
            for(x in (registrocategoria as ArrayList<Categoria>).indices){
                if((registrocategoria as ArrayList<Categoria>).get(x).nombre== (registroproducto as ArrayList<Producto>).get(fila).categoria?.nombre){
                    indice=x
                }
            }
            cboCat.setSelection(indice)
            if((registroproducto as ArrayList<Producto>).get(fila).estado){
                chkEstPro.setChecked(true)
            }else{
                chkEstPro.setChecked(false)
            }
        }

        btnActualizarPro.setOnClickListener {
            if(fila>=0){
                //capturando valores
                cod=lblCodPro.text.toString().toLong()
                nom=txtNomPro.text.toString()
                prec=txtPrec.text.toString().toDouble()
                stock=txtStock.text.toString().toInt()
                pos=cboCat.selectedItemPosition
                codcat= (registrocategoria as ArrayList<Categoria>).get(pos).codigo
                nomcat= (registrocategoria as ArrayList<Categoria>).get(pos).nombre.toString()
                est=if(chkEstPro.isChecked){
                    true
                }else{
                    false
                }

                //enviamos los valores a la clase
                objproducto.nombre=nom
                objproducto.preciosoles=prec
                objproducto.stock=stock

                objcategoria.codigo=codcat
                objproducto.categoria=objcategoria

                objproducto.estado=est

                //llamamos a la funcion para registrar
                ActualizarProducto(raiz.context,objproducto,cod)
                val fproducto=FragmentoProducto()
                DialogoCRUD("Actualizacion de Producto","Se actualizo el producto correctamente",fproducto)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstPro.requestFocus()
            }

        }


        btnEliminarPro.setOnClickListener {
            if(fila>=0){
                cod=lblCodPro.text.toString().toLong()
                nom=txtNomPro.text.toString()


                //llamamos a la funcion para registrar
                EliminarProducto(raiz.context,cod)
                val fproducto=FragmentoProducto()
                DialogoCRUD("Eliminacion de Producto","Se elimino el producto correctamente",fproducto)
            }else{
                objutilidad.MensajeToast(raiz.context,"Seleccione un elemento de la lista")
                lstPro.requestFocus()
            }
        }

        return raiz;
    }
    //creamos una funcion para mostrar el combo de la categoria
    fun MostrarComboCategoria(context: Context?){
        val call= categoriaService!!.MostrarCategoriaPersonalizado()
        call!!.enqueue(object : Callback<List<Categoria>?> {
            override fun onResponse(
                call: Call<List<Categoria>?>,
                response: Response<List<Categoria>?>
            ) {
                if(response.isSuccessful){
                    registrocategoria=response.body()
                    cboCat.adapter= AdaptadorComboCategoria(context,registrocategoria)


                }
            }

            override fun onFailure(call: Call<List<Categoria>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para mostrar los producto
    fun MostrarProducto(context: Context?){
        val call= productoService!!.MostrarProductoPersonalizado()
        call!!.enqueue(object : Callback<List<Producto>?> {
            override fun onResponse(
                call: Call<List<Producto>?>,
                response: Response<List<Producto>?>
            ) {
                if(response.isSuccessful){
                    registroproducto=response.body()
                    lstPro.adapter= AdaptadorProducto(context,registroproducto)
                }
            }

            override fun onFailure(call: Call<List<Producto>?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }


    //creamos una funcion para registrar producto
    fun RegistrarProducto(context: Context?, p: Producto?){
        val call= productoService!!.RegistrarProducto(p)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se registro")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para actualizar producto
    fun ActualizarProducto(context: Context?, p: Producto?, id:Long){
        val call= productoService!!.ActualizarProducto(id,p)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se actualizo")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para eliminar
    fun EliminarProducto(context: Context?, id:Long){
        val call= productoService!!.EliminarProducto(id)
        call!!.enqueue(object : Callback<Producto?> {
            override fun onResponse(call: Call<Producto?>, response: Response<Producto?>) {
                if(response.isSuccessful){
                    Log.e("mensaje","Se elimino")
                }
            }

            override fun onFailure(call: Call<Producto?>, t: Throwable) {
                Log.e("Error: ", t.message!!)
            }


        })
    }

    //creamos una funcion para los cuadros de dialogo del CRUD
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

    }
}