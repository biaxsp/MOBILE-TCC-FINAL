package br.senai.sp.jandira.mobile_maquiagem.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Tela(val rota: String) {
    object Inicio : Tela("tela_inicio")
    object Servicos : Tela("tela_servicos")
    object Agenda : Tela("tela_agenda/{servicoId}") {
        fun criarRota(servicoId: Int) = "tela_agenda/$servicoId"
    }
    // Alteramos para usar um placeholder seguro para evitar crash com barras
    object Confirmacao : Tela("tela_confirmacao/{data}/{hora}") {
        fun criarRota(data: String, hora: String): String {
            // Codificar barras (/) da data para URL-safe (ex: 27/11/2025 -> 27%2F11%2F2025)
            val dataEncoded = URLEncoder.encode(data, StandardCharsets.UTF_8.toString())
            val horaEncoded = URLEncoder.encode(hora, StandardCharsets.UTF_8.toString())
            return "tela_confirmacao/$dataEncoded/$horaEncoded"
        }
    }
}
