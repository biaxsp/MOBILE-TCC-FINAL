package br.senai.sp.jandira.mobile_maquiagem.model

import br.senai.sp.jandira.mobile_maquiagem.R
import com.google.gson.annotations.SerializedName

data class Servico(
    val id: Int,

    @SerializedName(value = "nome", alternate = ["nomeServico", "nome_servico"])
    val nome: String,

    val descricao: String? = null,
    val duracao: Int? = null, // em minutos

    // Ajuste: preco pode vir como String da API (como visto no log), entao o Gson converte automaticamente se for Double,
    // mas se for String "280", ele pode ter problemas se nao estiver bem configurado.
    // O log mostrou "preco":"280". Double deve lidar bem com isso, mas vamos garantir.
    val preco: Double,

    val imagem_url: String? = null,

    // Campo local para o recurso de imagem, não mapeado da API
    val imagemRes: Int? = null
)

// Dados fictícios APENAS para preview, a API deve substituir isso
val listaServicos = listOf(
    Servico(1, "VOLUME\nRUSSO", "Técnica de volume russo", 120, 280.00, null, R.drawable.cilios1),
    Servico(2, "VOLUME FIO\nA FIO", "Técnica fio a fio clássica", 90, 230.00, null, R.drawable.cilios2),
    Servico(3, "VOLUME\nBRASILEIRO", "Técnica volume brasileiro", 100, 260.00, null, R.drawable.cilios3),
    Servico(4, "VOLUME\nMOLHADO", "Efeito molhado", 110, 310.00, null, R.drawable.cilios4),
    Servico(5, "VOLUME\nHIBRIDO", "Mistura de técnicas", 110, 280.00, null, R.drawable.cilios5),
    Servico(6, "VOLUME\nEGÍPCIO", "Técnica egípcia", 120, 280.00, null, R.drawable.cilios6)
)
