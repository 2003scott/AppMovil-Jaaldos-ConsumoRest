package pe.com.appjaaldos.clases

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Producto {

    @SerializedName("codigo")
    @Expose
    var codigo:Long = 0L

    @SerializedName("nombre")
    @Expose
    var nombre:String?=null

    @SerializedName("categoria")
    @Expose
    var categoria:Categoria?=null

    @SerializedName("stock")
    @Expose
    var stock:Int = 0

    @SerializedName("preciosoles")
    @Expose
    var preciosoles:Double = 0.0

    @SerializedName("estado")
    @Expose
    var estado:Boolean = false

    constructor(){

    }

    constructor(codigo: Long, nombre: String?, categoria: Categoria?, stock: Int, preciosoles: Double, estado: Boolean) {
        this.codigo = codigo
        this.nombre = nombre
        this.categoria = categoria
        this.stock = stock
        this.preciosoles = preciosoles
        this.estado = estado
    }


}