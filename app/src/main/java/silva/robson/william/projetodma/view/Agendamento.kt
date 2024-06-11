package silva.robson.william.projetodma.view

import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import silva.robson.william.projetodma.databinding.ActivityAgendamentoBinding
import java.time.LocalTime

class Agendamento : AppCompatActivity() {

    // Declaração de variáveis de instância
    private lateinit var binding: ActivityAgendamentoBinding // Binding para o layout
    private val calendar: Calendar = Calendar.getInstance() // Objeto Calendar para manipular datas e horas
    private var data: String = "" // Variável para armazenar a data selecionada
    private var hora: String = "" // Variável para armazenar a hora selecionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendamentoBinding.inflate(layoutInflater) // Inflar o layout usando o binding
        setContentView(binding.root) // Definir o layout da atividade

        supportActionBar?.hide() // Ocultar a barra de ação

        // Obter dados passados para a atividade
        val nome = intent.extras?.getString("nome").toString()
        val placa= intent.extras?.getString("placa").toString()

        // Configurar listener para o DatePicker
        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener{ _,year,monthOfYear,dayOfMonth ->

            // Atualizar o objeto Calendar com a data selecionada
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            // Formatar a data selecionada
            var dia = dayOfMonth.toString()
            val mes = (monthOfYear + 1).toString()
            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            data = "$dia / $mes / $year" // Armazenar a data formatada
        }

        // Configurar listener para o TimePicker
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Formatar a hora selecionada
            val horaFormatada = "%02d:%02d".format(hourOfDay, minute)
            hora = horaFormatada // Armazenar a hora formatada
        }
        binding.timePicker.setIs24HourView(true) // Configurar o TimePicker para exibir o formato de 24 horas

        // Configurar listener para o botão "Agendar"
        binding.btAgendar.setOnClickListener{

            // Obter as checkboxes das unidades
            val unidade1 = binding.unidade1
            val unidade2 = binding.unidade2
            val unidade3 = binding.unidade3

            // Verificar as condições para o agendamento
            when{
                hora.isEmpty() -> {
                    mensagem(it,"Preencha o horário","#FF0000")
                }
                !validarHorario(hora) -> {
                    mensagem(it, "Horário de atendimento das 08:00 as 17:00.", "#FF0000")
                }
                data.isEmpty() -> {
                    mensagem(it,"Selecione uma data.","#FF0000")
                }
                unidade1.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it,nome,placa,"Boa Viagem",data,hora)
                }
                unidade2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it,nome,placa,"Imbiribeira",data,hora)
                }
                unidade3.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it,nome,placa,"Madalena",data,hora)
                }
                else -> {
                    mensagem(it,"Escolha a unidade de sua preferência.","#FF0000")
                }
            }
        }
    }

    // Método para validar o horário
    private fun validarHorario(horario: String): Boolean {
        val horarioAgendamento = LocalTime.parse(horario)
        val inicioAtendimento = LocalTime.parse("08:00")
        val fimAtendimento = LocalTime.parse("17:00")
        return horarioAgendamento in inicioAtendimento..fimAtendimento
    }

    // Método para exibir uma mensagem usando um Snackbar
    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    // Método para salvar o agendamento no Firestore
    private fun salvarAgendamento(view: View, cliente: String, placa: String, unidade:String, data: String, hora: String){
        val db = FirebaseFirestore.getInstance() // Obter uma instância do Firestore
        val dadosUsuario = hashMapOf(
            "Cliente" to cliente,
            "Unidade" to unidade,
            "Data" to data,
            "Hora" to hora,
            "Placa" to placa
        ) // Criar um mapa com os dados do agendamento
        db.collection("agendamento").document(cliente).set(dadosUsuario) // Salvar o agendamento no Firestore
            .addOnCompleteListener {
                mensagem(view,"Agendamento Realizado com Sucesso!","#FF03DAC5") // Exibir mensagem de sucesso
            }
            .addOnFailureListener {
                mensagem(view,"Erro ao Salvar!","#FF0000") // Exibir mensagem de erro
            }
    }
}
