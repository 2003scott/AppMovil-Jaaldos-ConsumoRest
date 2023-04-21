package pe.com.appjaaldos.servicios

import pe.com.appjaaldos.clases.Proveedor
import retrofit2.Call
import retrofit2.http.*

interface ProveedorService {

    //creamos los metodos para acceder al servicio web
    @GET("proveedor")
    fun MostrarProveedor(): Call<List<Proveedor>?>?

    @GET("proveedor/custom")
    fun MostrarProveedorPersonalizado(): Call<List<Proveedor>?>?

    @POST("proveedor")
    fun RegistrarProveedor(@Body prov: Proveedor?): Call<Proveedor?>?

    @PUT("proveedor/{id}")
    fun ActualizarProveedor(@Path("id") id:Long, @Body prov: Proveedor?): Call<Proveedor?>?

    @DELETE("proveedor/{id}")
    fun EliminarProveedor(@Path("id") id:Long): Call<Proveedor?>?
}