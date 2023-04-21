package pe.com.appjaaldos.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Proveedor {


    @SerializedName("codigo")
    @Expose
    var codigo: Long = 0L

    @SerializedName("nombre")
    @Expose
    var nombre: String? = null

    @SerializedName("correo")
    @Expose
    var correo: String? = null

    @SerializedName("telefono")
    @Expose
    var telefono: String? = null

    @SerializedName("ubicacion")
    @Expose
    var ubicacion:Ubicacion?=null

    @SerializedName("direccion")
    @Expose
    var direccion:String?=null

    @SerializedName("estado")
    @Expose
    var estado:Boolean = false

    constructor(){

    }

    constructor(codigo: Long, nombre: String?, correo: String?, telefono: String?, ubicacion: Ubicacion?, direccion: String?, estado: Boolean) {
        this.codigo = codigo
        this.nombre = nombre
        this.correo = correo
        this.telefono = telefono
        this.ubicacion = ubicacion
        this.direccion = direccion
        this.estado = estado
    }


}