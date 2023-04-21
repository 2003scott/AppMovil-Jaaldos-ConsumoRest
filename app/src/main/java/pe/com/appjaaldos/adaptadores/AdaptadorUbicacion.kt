package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Ubicacion

class AdaptadorUbicacion (context: Context?,private val listaubicacion:List<Ubicacion>?):BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private var listaFiltrada: List<Ubicacion>? = null

    init {
        layoutInflater= LayoutInflater.from(context)
        listaFiltrada = listaubicacion
    }

    fun filter(texto: String) {
        listaFiltrada = if (texto.isEmpty()) {
            listaubicacion
        } else {
            listaubicacion?.filter {
                it.provincia!!.lowercase().contains(texto.lowercase())
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

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        val objubicacion = listaFiltrada!![position]

        if (vista==null) {
            vista = layoutInflater.inflate(R.layout.elemento_lista_ubicacion, p2, false)
        }
            //controles
            val lstCodUbi=vista!!.findViewById<TextView>(R.id.lstCodUbi)
            val lstProUbi=vista!!.findViewById<TextView>(R.id.lstProUbi)
            val lstDisUbi=vista!!.findViewById<TextView>(R.id.lstDisUbi)
            val lstEstUbi=vista!!.findViewById<TextView>(R.id.lstEstUbi)
            //AGREGAMOS VALORES A LOS CONTROLES
            lstCodUbi.text=""+objubicacion.codigo
            lstProUbi.text=""+objubicacion.provincia
            lstDisUbi.text=""+objubicacion.distrito
            if(objubicacion.estado==true){
                lstEstUbi.text="Habilitado"
            }else{
                lstEstUbi.text="Deshabilitado"
            }
        return vista!!
    }
}