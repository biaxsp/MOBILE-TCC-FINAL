package br.senai.sp.jandira.mobile_maquiagem.model

data class Cliente(
    val id: Int? = null,
    val nome: String,
    val telefone: String,
    val email: String? = null,
    val foto: String? = null
)
