package br.senai.sp.jandira.mobile_maquiagem.model

data class Profissional(
    val id: Int,
    val nome: String,
    val especialidade: String? = null,
    val telefone: String? = null,
    val foto: String? = null
)
