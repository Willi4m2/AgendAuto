package silva.robson.william.projetodma.view

import android.content.Intent
import android.graphics.drawable.RippleDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import silva.robson.william.projetodma.R
import silva.robson.william.projetodma.adapter.ServicosAdapter
import silva.robson.william.projetodma.databinding.ActivityHomeBinding
import silva.robson.william.projetodma.model.Servicos

class Home : AppCompatActivity() {

    //Vincular os componentes da interface do usuário no arquivo de layout activity_home
    // Instância de ServicosAdapter, exibir listas de serviços
    //Lista mutável de obj Servicos, armazenamento temporario antes de exibidos na interface.

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    //método onCreate da classe que estende Activity, infla o layout da atividade usando o ActivityHomeBinding.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Ocultar barra de ação
        supportActionBar?.hide()

        //Recuperar o nome do usuário e placa do veiculo.
        val placa = intent.extras?.getString("placa")
        val nome = intent.extras?.getString("nome")
        binding.txtNomeUsuario.text = "Bem-vindo(a), $nome"

        //Configurando recyclerView, que exibirá a lista de serviços
        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter

        //Inicializar a lista
        getServicos()

        binding.btAgendar.setOnClickListener{
            val intent = Intent(this,Agendamento::class.java)
            intent.putExtra("nome",nome)
            intent.putExtra("placa",placa)
            startActivity(intent)
        }
    }

    //Metodo para adiciona serviços a lista
    private fun getServicos() {
        val servico1 = Servicos(R.drawable.icon_rev, "Revisão")
        listaServicos.add(servico1)

        val servico2 = Servicos(R.drawable.icon_pneu, "Alinhamento/Balanceamento")
        listaServicos.add(servico2)

        val servico3 = Servicos(R.drawable.icon_susp, "Suspensão")
        listaServicos.add(servico3)

        val servico4 = Servicos(R.drawable.icon_ar, "Ar condicionado")
        listaServicos.add(servico4)
    }
}