package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Categoria

class AdaptadorCategoria(context: Context?, private val listacategoria: List<Categoria>?): BaseAdapter() {

    private val layoutInflater:LayoutInflater
    private var listaFiltrada: List<Categoria>? = null


    init{
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listacategoria
    }

    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listacategoria
        } else {
            listacategoria?.filter {
                it.nombre!!.lowercase().contains(texto.lowercase())
                //e -> e.nombre!!.toLowerCase(Locale.getDefault()).contains(texto.toLowerCase(Locale.getDefault()))!!
                //|| e.estado.toString()!!.toLowerCase(Locale.getDefault())?.contains(texto.toLowerCase(Locale.getDefault()))!!
            }
        }
        notifyDataSetChanged()

    }

    override fun getCount(): Int {
        return listaFiltrada!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaFiltrada!![p0]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vista=convertView
        val objcategoria = listaFiltrada!![position]

        if(vista==null) {
            vista=layoutInflater.inflate(R.layout.elemento_lista_categoria,parent,false)
        }

        //creamos los controles
        val lblCodCat= vista!!.findViewById<TextView>(R.id.lstCodCat)
        val lblNomCat= vista!!.findViewById<TextView>(R.id.lstNomCat)
        val lblEstCat= vista!!.findViewById<TextView>(R.id.lstEstCat)
        //agregamos los valores a la lista
        lblCodCat.text=""+objcategoria.codigo
        lblNomCat.text=""+objcategoria.nombre
        if(objcategoria.estado==true){
            lblEstCat.text="Habilitado"
        }else{
            lblEstCat.text="Deshabilitado"
        }
        return vista!!
    }

}