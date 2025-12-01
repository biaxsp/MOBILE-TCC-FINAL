package br.senai.sp.jandira.mobile_maquiagem.model

import com.google.gson.annotations.SerializedName

data class Horario(
    val id: Int,

    @SerializedName("dia_semana")
    val dia_semana: String, // "Segunda-feira", etc.

    @SerializedName(value = "horario", alternate = ["horario_inicio"])
    val horario: String, // Aceita "horario" ou "horario_inicio" do JSON

    val status: Boolean = true // Default true caso não venha
)
