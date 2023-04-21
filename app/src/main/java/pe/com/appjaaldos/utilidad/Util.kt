package pe.com.appjaaldos.utilidad

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.ViewGroup
import android.widget.*

class Util {

    //creamos una variable de tipo AlertDialog
    private var dialogo: AlertDialog.Builder?=null

    //creamos un procedimiento para salir
    fun SalirSistema(context: Context){
        dialogo= AlertDialog.Builder(context)
        dialogo!!.setTitle("Saliendo del Sistema")
        dialogo!!.setMessage("¿Estas seguro que quieres salir del sistema?")
        dialogo!!.setCancelable(false)
        dialogo!!.setPositiveButton("Si"){
                dialog,which->(context as Activity).finish()
        }
        dialogo!!.setNegativeButton("No"){
                dialog,which->
        }
        dialogo!!.show()
    }

    //creamos un procedimiento para mostrar los mensajes en Toast
    fun MensajeToast(context: Context, men:String){
        Toast.makeText(context,men, Toast.LENGTH_LONG).show()
    }

    //creamos un procedimiento para limpiar los controles
    fun Limpiar(viewGroup: ViewGroup){
        var i=0
        val count=viewGroup.childCount
        while(i<count){
            val view=viewGroup.getChildAt(i)
            //caja de texto
            if(view is EditText){
                view.setText("")
            }
            //radio button
            if(view is RadioGroup){
                (view.getChildAt(0) as RadioButton).isChecked=false
            }
            //para el spinner
            if(view is Spinner){
                view.setSelection(0)
            }
            //para el checkbox
            if(view is CheckBox){
                view.isChecked=false
            }
            if(view is ViewGroup && view.childCount>0){
                Limpiar(view as ViewGroup)
            }
            i++
        }
    }
}