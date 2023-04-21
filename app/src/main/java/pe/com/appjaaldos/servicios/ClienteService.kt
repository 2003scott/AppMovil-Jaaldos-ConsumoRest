package pe.com.appjaaldos.servicios

import pe.com.appjaaldos.clases.Cliente
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {

    //creamos los metodos para acceder al servicio web
    @GET("clientes")
    fun MostrarCliente(): Call<List<Cliente>?>?

    @GET("clientes/custom")
    fun MostrarClientePersonalizado(): Call<List<Cliente>?>?

    @POST("clientes")
    fun RegistrarCliente(@Body cli: Cliente?): Call<Cliente?>?

    @PUT("clientes/{id}")
    fun ActualizarCliente(@Path("id") id:Long, @Body cli: Cliente?): Call<Cliente?>?

    @DELETE("clientes/{id}")
    fun EliminarCliente(@Path("id") id:Long): Call<Cliente?>?
}