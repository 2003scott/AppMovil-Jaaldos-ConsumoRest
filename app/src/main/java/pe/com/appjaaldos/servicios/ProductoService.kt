package pe.com.appjaaldos.servicios

import pe.com.appjaaldos.clases.Producto
import retrofit2.Call
import retrofit2.http.*

interface ProductoService {
    //creamos los metodos para acceder al servicio web
    @GET("productos")
    fun MostrarProducto(): Call<List<Producto>?>?

    @GET("productos/custom")
    fun MostrarProductoPersonalizado(): Call<List<Producto>?>?

    @POST("productos")
    fun RegistrarProducto(@Body p: Producto?): Call<Producto?>?

    @PUT("productos/{id}")
    fun ActualizarProducto(@Path("id") id:Long, @Body p: Producto?): Call<Producto?>?

    @DELETE("productos/{id}")
    fun EliminarProducto(@Path("id") id:Long): Call<Producto?>?
}