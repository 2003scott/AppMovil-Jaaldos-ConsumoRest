package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Proveedor

class AdaptadorProveedor (context: Context?, private val listarproveedor:List<Proveedor>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listarproveedor!!.size
    }

    override fun getItem(p0: Int): Any {
        return listarproveedor!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relaciona
            //e
            vista=layoutInflater.inflate(R.layout.elemento_lista_proveedor,p2,false)
            val objproveedor=getItem(p0) as Proveedor
            //creamos los controles
            val lstCodProv= vista!!.findViewById<TextView>(R.id.lstCodProv)
            val lstNomProv= vista!!.findViewById<TextView>(R.id.lstNomProv)
            val lstTelProv= vista!!.findViewById<TextView>(R.id.lstTelProv)
            val lstCorProv= vista!!.findViewById<TextView>(R.id.lstCorProv)
            val lstUbiProv= vista!!.findViewById<TextView>(R.id.lstUbiProv)
            val lstDirProv= vista!!.findViewById<TextView>(R.id.lstDirProv)
            val lstEstProv= vista!!.findViewById<TextView>(R.id.lstEstProv)
            //agregamos los valores a la lista
            lstCodProv.text=""+objproveedor.codigo
            lstNomProv.text=""+objproveedor.nombre
            lstTelProv.text=""+objproveedor.telefono
            lstCorProv.text=""+objproveedor.correo
            lstUbiProv.text=""+objproveedor.ubicacion
            lstDirProv.text=""+objproveedor.direccion
            if(objproveedor.estado==true){
                lstEstProv.text="Habilitado"
            }else{
                lstEstProv.text="Deshabilitado"
            }
            lstEstProv.text=""+ objproveedor.ubicacion!!.distrito
        }
        return vista!!
    }
}