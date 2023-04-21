package pe.com.appjaaldos.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.appjaaldos.R
import pe.com.appjaaldos.clases.Ubicacion

class AdaptadorComboUbicacion (context: Context?, private val listaubicacion:List<Ubicacion>?): BaseAdapter() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listaubicacion!!.size
    }

    override fun getItem(p0: Int): Any {
        return listaubicacion!![p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var vista=p1
        if(vista==null){
            //relacionamos la vista con el layout correspondiente
            //en este caso elemento_lista_categoria
            vista=layoutInflater.inflate(R.layout.elemento_combo_ubicacion,p2,false)
            val objubicacion=getItem(p0) as Ubicacion
            //creamos los controles
            val lblDisUbi= vista!!.findViewById<TextView>(R.id.lblDisUbi)

            //agregamos los valores a la lista
            lblDisUbi.text=""+objubicacion.distrito
        }
        return vista!!
    }
}