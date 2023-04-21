package pe.com.appjaaldos.servicios

import pe.com.appjaaldos.clases.Ubicacion
import retrofit2.Call
import retrofit2.http.*

interface UbicacionService {

    @GET("ubicacion")
    fun MostrarUbicacion(): Call<List<Ubicacion>>

    @GET("ubicacion/custom")
    fun MostrarUbicacionPersonalizado(): Call<List<Ubicacion>>

    @POST("ubicacion")
    fun RegistrarUbicacion(@Body u: Ubicacion?): Call<List<Ubicacion>>

    @PUT("ubicacion/{id}")
    fun ActualizarUbicacion(@Path("id") id:Long,@Body u: Ubicacion?): Call<List<Ubicacion>>

    @DELETE("ubicacion/{id}")
    fun EliminarUbicacion(@Path("id") id:Long): Call<List<Ubicacion>>
}