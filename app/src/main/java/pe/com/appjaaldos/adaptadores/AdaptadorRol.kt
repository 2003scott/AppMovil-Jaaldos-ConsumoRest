package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Rol


class AdaptadorRol(context: Context?, private val listarol:List<Rol>?): BaseAdapter(){

    private val layoutInflater: LayoutInflater
    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listarol!!.size
    }

    override fun getItem(p0: Int): Any {
        return listarol!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if (vista == null){
            vista=layoutInflater.inflate(R.layout.elemento_lista_rol,p2,false)
        val objrol=getItem(p0) as Rol
            //creamos los controles
            val lstCodRol=vista!!.findViewById<TextView>(R.id.lstCodRol)
            val lstCarRol=vista!!.findViewById<TextView>(R.id.lstCarRol)
            val lstEstRol=vista!!.findViewById<TextView>(R.id.lstEstRol)
            //agreamos valores a los controles
            lstCodRol.text=""+objrol.codigo
            lstCarRol.text=""+objrol.cargo
            if(objrol.estado==true){
                lstEstRol.text="Habilitado"
            }else{
                lstEstRol.text="Deshabilitado"
            }
        }
        return vista!!
    }
}