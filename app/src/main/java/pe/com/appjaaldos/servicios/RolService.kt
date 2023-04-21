package pe.com.appjaaldos.servicios

import pe.com.appjaaldos.clases.Rol
import retrofit2.Call
import retrofit2.http.*

interface RolService {


    @GET("rolcargo")
    fun MostrarRol(): Call<List<Rol>>

    @GET("rolcargo/custom")
    fun MostrarRolPersonalizado(): Call<List<Rol>>

    @POST("rolcargo")
    fun RegistrarRol(@Body r: Rol?): Call<List<Rol>>

    @PUT("rolcargo/{id}")
    fun ActualizarRol(@Path("id") id:Long, @Body r: Rol?): Call<List<Rol>>

    @DELETE("rolcargo/{id}")
    fun EliminarRol(@Path("id") id:Long): Call<List<Rol>>
}