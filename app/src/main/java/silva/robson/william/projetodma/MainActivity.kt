package silva.robson.william.projetodma

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import silva.robson.william.projetodma.databinding.ActivityMainBinding
import silva.robson.william.projetodma.view.Home

class MainActivity : AppCompatActivity(){
    //acessar os componentes da interface do usuário no arquivo de layout activity_main
    private lateinit var binding: ActivityMainBinding

    //Iniciando a atividade e config layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ocultar barra da atividade
        supportActionBar?.hide()

        //atividade click botão login
        binding.btLogin.setOnClickListener{

            val nome = binding.editNome.text.toString()
            val placa = binding.editPlaca.text.toString()


            //Condição para validação dos dados do usuário.
            when{
                nome.isEmpty() -> {
                    mensagem(it,"Informe o seu nome!")
                }placa.isEmpty() -> {
                mensagem(it,"Informe a placa do veículo!")
                }placa.length <=6 -> {
                mensagem(it,"A Placa precisa ter pelo menos 7 caracteres!")
                }else -> {
                navegarPraHome(nome,placa)}

            }

        }

    }

    //exibe uma mensagem temporária (usando um Snackbar) na view
    private fun mensagem(view: View, mensagem: String){
        val snackbar = Snackbar.make(view,mensagem,Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
    //cria um Intent para navegar para a atividade Home, passando os valores do nome e da placa como extras.
    private fun navegarPraHome(nome: String, placa: String){
        val intent = Intent(this,Home::class.java)
        intent.putExtra("nome",nome)
        intent.putExtra("placa",placa)
        startActivity(intent)

    }
}


