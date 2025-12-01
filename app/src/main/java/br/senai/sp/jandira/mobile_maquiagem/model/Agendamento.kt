package br.senai.sp.jandira.mobile_maquiagem.model

data class Agendamento(
    val id: Int? = null,
    val data_agendamento: String, // formato ISO: "2024-11-06T15:30:00.000Z" ou apenas data "2024-11-06"
    val horario: String, // "15:30"
    val status_agendamento: String, // "Pendente", "Confirmado", "Cancelado"
    val valor: Double,
    val id_cliente: Int,
    val id_profissional: Int,
    val id_servico: Int,

    // Campos opcionais para retorno com JOIN (se a API retornar objetos aninhados)
    val cliente: Cliente? = null,
    val profissional: Profissional? = null,
    val servico: Servico? = null
)
