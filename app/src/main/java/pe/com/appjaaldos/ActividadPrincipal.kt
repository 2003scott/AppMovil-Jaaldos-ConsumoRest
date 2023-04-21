package pe.com.appjaaldos

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import pe.com.appjaaldos.databinding.ActivityMainBinding

class ActividadPrincipal : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //creamos una constante para poder manejar las opciones del menu
        val id=item.itemId

        return when (id) {
            R.id.jmiInicio ->{
                //creamos una constante del fragmento que vamos a cambiar
                val finicio=FragmentoInicio()
                //en el contenedor reemplazamos con el fragmento requerido
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,finicio).commit()
                true
            }

            R.id.jmiBuscarCategoria  ->{
                val fbuscarcategoria=FragmentoBuscarCategoria()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarcategoria).commit()
                true
            }

            R.id.jmiBuscarUbicacion  ->{
                val fbuscarubicacion=FragmentoBuscarUbicacion()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarubicacion).commit()
                true
            }

            R.id.jmiInicio ->{
                val finicio=FragmentoInicio()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,finicio).commit()
                true
            }

            R.id.jmiCategoria ->{
                val fcategoria=FragmentoCategoria()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fcategoria).commit()
                true
            }
            R.id.jmiUbicacion ->{
                val fubicacion=FragmentoUbicacion()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fubicacion).commit()
                true
            }
            R.id.jmiProducto ->{
                val fproducto=FragmentoProducto()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fproducto).commit()
                true
            }
            R.id.jmiProveedor ->{
                val fproveedor=FragmentoProveedor()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fproveedor).commit()
                true
            }
            R.id.jmiCliente ->{
                val fcliente=FragmentoCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fcliente).commit()
                true
            }
            R.id.jmiRol ->{
                val frol=FragmentoRol()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frol).commit()
                true
            }
            R.id.jmiCerrarSesion->{
                val formulario= Intent(this,ActividadIngreso::class.java)
                startActivity(formulario)
                this.finish()
                true
            }
            R.id.jmiSalir->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}