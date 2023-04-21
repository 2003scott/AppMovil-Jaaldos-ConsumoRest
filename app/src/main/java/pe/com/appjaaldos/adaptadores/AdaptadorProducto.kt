package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Producto

class AdaptadorProducto(context: Context?, private val listaproducto:List<Producto>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater= LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaproducto!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaproducto!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_lista_producto,p2,false)
            val objproducto=getItem(p0) as Producto
            //creamos los controles
            val lstCodPro= vista!!.findViewById<TextView>(R.id.lstCodPro)
            val lstNomPro= vista!!.findViewById<TextView>(R.id.lstNomPro)
            val lstCatPro= vista!!.findViewById<TextView>(R.id.lstCatPro)
            val lstPrecPro= vista!!.findViewById<TextView>(R.id.lstPrecPro)
            val lstStock= vista!!.findViewById<TextView>(R.id.lstStockPro)
            val lstEstPro= vista!!.findViewById<TextView>(R.id.lstEstPro)
            //agregamos los valores a la lista
            lstCodPro.text=""+objproducto.codigo
            lstNomPro.text=""+objproducto.nombre
            lstCatPro.text=""+objproducto.categoria
            lstPrecPro.text=""+objproducto.preciosoles
            lstStock.text=""+objproducto.stock
            if(objproducto.estado==true){
                lstEstPro.text="Habilitado"
            }else{
                lstEstPro.text="Deshabilitado"
            }
            lstCatPro.text=""+ objproducto.categoria!!.nombre
        }
        return vista!!
    }
}