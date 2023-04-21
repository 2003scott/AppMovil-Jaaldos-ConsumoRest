package pe.com.appjaaldos.remoto

import pe.com.appjaaldos.servicios.*

object ApiUtil {

    val API_URl="http://ACA-TU-IP/jaaldos/"

    val rolService:RolService?
        get() =RetrofitClient.getClient(API_URl)?.create(RolService::class.java)

    val ubicacionService:UbicacionService?
        get() =RetrofitClient.getClient(API_URl)?.create(UbicacionService::class.java)

    val categoriaService:CategoriaService?
        get() =RetrofitClient.getClient(API_URl)?.create(CategoriaService::class.java)

    val productoService:ProductoService?
        get() =RetrofitClient.getClient(API_URl)?.create(ProductoService::class.java)

    val clienteService: ClienteService?
        get() =RetrofitClient.getClient(API_URl)?.create(ClienteService::class.java)

    val proveedorService: ProveedorService?
        get() =RetrofitClient.getClient(API_URl)?.create(ProveedorService::class.java)


}