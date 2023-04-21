package pe.com.appjaaldos.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cliente {

    @SerializedName("codigo")
    @Expose
    var codigo:Long = 0L

    @SerializedName("nombres")
    @Expose
    var nombre:String?=null

    @SerializedName("apellidos")
    @Expose
    var apellidos:String?=null

    @SerializedName("dni")
    @Expose
    var dni:String?=null

    @SerializedName("telefono")
    @Expose
    var telefono:String?=null

    @SerializedName("correo")
    @Expose
    var correo:String?=null

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

    constructor(codigo: Long, nombre: String?, apellidos: String?, dni: String?, telefono: String?, correo: String?, ubicacion: Ubicacion?, direccion: String?, estado: Boolean) {
        this.codigo = codigo
        this.nombre = nombre
        this.apellidos = apellidos
        this.dni = dni
        this.telefono = telefono
        this.correo = correo
        this.ubicacion = ubicacion
        this.direccion = direccion
        this.estado = estado
    }


}