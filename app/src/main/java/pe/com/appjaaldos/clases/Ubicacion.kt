package pe.com.appjaaldos.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Ubicacion {

    @SerializedName("codigo")
    @Expose
    var codigo:Long = 0

    @SerializedName("provincia")
    @Expose
    var provincia:String? = null

    @SerializedName("distrito")
    @Expose
    var distrito:String? = null

    @SerializedName("estado")
    @Expose
    var estado:Boolean = false

    constructor(){

    }

    constructor(codigo: Long, provincia: String?, distrito: String?, estado: Boolean) {
        this.codigo = codigo
        this.provincia = provincia
        this.distrito = distrito
        this.estado = estado
    }


}