package pe.com.appjaaldos.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rol {

    @SerializedName("codigo")
    @Expose
    var codigo:Long = 0

    @SerializedName("cargo")
    @Expose
    var cargo:String? = null

    @SerializedName("estado")
    @Expose
    var estado:Boolean = false

    constructor(){

    }

    constructor(codigo: Long, cargo: String?, estado: Boolean) {
        this.codigo = codigo
        this.cargo = cargo
        this.estado = estado
    }


}