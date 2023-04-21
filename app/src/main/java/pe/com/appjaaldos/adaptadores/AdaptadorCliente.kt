package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Cliente

class AdaptadorCliente(context: Context?, private val listarcliente:List<Cliente>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listarcliente!!.size
    }

    override fun getItem(p0: Int): Any {
        return listarcliente!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relaciona
            //e
            vista=layoutInflater.inflate(R.layout.elemento_lista_cliente,p2,false)
            val objcliente=getItem(p0) as Cliente
            //creamos los controles
            val lstCodCli= vista!!.findViewById<TextView>(R.id.lstCodCli)
            val lstNomCli= vista!!.findViewById<TextView>(R.id.lstNomCli)
            val lstApeCli= vista!!.findViewById<TextView>(R.id.lstApeCli)
            val lstDniCli= vista!!.findViewById<TextView>(R.id.lstDniCli)
            val lstTelCli= vista!!.findViewById<TextView>(R.id.lstTelCli)
            val lstCorCli= vista!!.findViewById<TextView>(R.id.lstCorCli)
            val lstUbiCli= vista!!.findViewById<TextView>(R.id.lstUbiCli)
            val lstDirCli= vista!!.findViewById<TextView>(R.id.lstDirCli)
            val lstEstCli= vista!!.findViewById<TextView>(R.id.lstEstCli)
            //agregamos los valores a la lista
            lstCodCli.text=""+objcliente.codigo
            lstNomCli.text=""+objcliente.nombre
            lstApeCli.text=""+objcliente.apellidos
            lstDniCli.text=""+objcliente.dni
            lstTelCli.text=""+objcliente.telefono
            lstCorCli.text=""+objcliente.correo
            lstUbiCli.text=""+objcliente.ubicacion
            lstDirCli.text=""+objcliente.direccion
            if(objcliente.estado==true){
                lstEstCli.text="Habilitado"
            }else{
                lstEstCli.text="Deshabilitado"
            }
            lstUbiCli.text=""+ objcliente.ubicacion!!.distrito
        }
        return vista!!
    }
}