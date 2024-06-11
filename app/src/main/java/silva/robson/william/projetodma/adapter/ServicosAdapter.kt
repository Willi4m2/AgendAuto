package silva.robson.william.projetodma.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import silva.robson.william.projetodma.databinding.ServicosItemBinding
import silva.robson.william.projetodma.model.Servicos

class ServicosAdapter (private val context: Context, private val listaServicos: MutableList<Servicos>):
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    //Chamado quando o RecyclerView precisa de um novo visualizador de itens para representar um item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val itemLista = ServicosItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ServicosViewHolder(itemLista)
    }
    //Retorna o número de itens na lista de serviços
    override fun getItemCount()= listaServicos.size

    //Método chamado pelo RecyclerView para exibir dados na posição especificada
    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.imgServico.setImageResource(listaServicos[position].img!!)
        holder.txtServico.text = listaServicos[position].nome
    }

    //Classe interna representa a ViewHolder para os itens do RecyclerView.
    inner class ServicosViewHolder(binding: ServicosItemBinding): RecyclerView.ViewHolder(binding.root){

        val imgServico = binding.imgServico
        val txtServico = binding.txtServico
    }
}