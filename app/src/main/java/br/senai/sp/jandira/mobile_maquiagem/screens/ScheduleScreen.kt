package br.senai.sp.jandira.mobile_maquiagem.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.mobile_maquiagem.R
import br.senai.sp.jandira.mobile_maquiagem.components.BotaoCustomizado
import br.senai.sp.jandira.mobile_maquiagem.components.Logo
import br.senai.sp.jandira.mobile_maquiagem.model.*
import br.senai.sp.jandira.mobile_maquiagem.service.RetrofitFactory
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaClinica
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaClinicaMedio
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.Branco
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TelaAgenda(navController: NavController, servicoId: Int = 0) {

    val context = LocalContext.current

    // Estados
    var servicoSelecionado by remember { mutableStateOf<Servico?>(null) }
    var horariosDisponiveis by remember { mutableStateOf<List<Horario>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Estados de seleção do usuário
    var dataSelecionada by remember { mutableStateOf(LocalDate.now()) }
    var horarioSelecionado by remember { mutableStateOf<Horario?>(null) }

    // Carregar dados do serviço e horários
    LaunchedEffect(servicoId) {
        // Carregar Serviço
        if (servicoId != 0) {
            // Alteração aqui: Usando o tipo ApiResponseService correto para retorno único
            RetrofitFactory.userService.getServicoById(servicoId).enqueue(object : Callback<ApiResponseService> {
                override fun onResponse(call: Call<ApiResponseService>, response: Response<ApiResponseService>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        // Agora acessamos .servico e não .data
                        if (apiResponse != null && apiResponse.servico != null) {
                            servicoSelecionado = apiResponse.servico
                        } else {
                            servicoSelecionado = listaServicos.find { it.id == servicoId }
                        }
                    } else {
                        servicoSelecionado = listaServicos.find { it.id == servicoId }
                    }
                }
                override fun onFailure(call: Call<ApiResponseService>, t: Throwable) {
                    Log.e("API", "Erro ao carregar serviço: ${t.message}")
                    servicoSelecionado = listaServicos.find { it.id == servicoId }
                }
            })
        } else {
            servicoSelecionado = listaServicos.firstOrNull()
        }

        // Carregar Horários Disponíveis
        RetrofitFactory.userService.getHorariosDisponiveis().enqueue(object : Callback<ApiResponse<List<Horario>>> {
            override fun onResponse(call: Call<ApiResponse<List<Horario>>>, response: Response<ApiResponse<List<Horario>>>) {
                if (response.isSuccessful && response.body()?.data != null) {
                    horariosDisponiveis = response.body()!!.data!!.filter { it.status }
                    isLoading = false
                } else {
                    horariosDisponiveis = listOf(
                        Horario(1, "Segunda-feira", "15:15", true),
                        Horario(2, "Segunda-feira", "15:30", true),
                        Horario(3, "Segunda-feira", "15:45", true),
                        Horario(4, "Segunda-feira", "16:00", true),
                        Horario(5, "Segunda-feira", "16:15", true)
                    )
                    isLoading = false
                }
            }
            override fun onFailure(call: Call<ApiResponse<List<Horario>>>, t: Throwable) {
                Log.e("API", "Erro horários: ${t.message}")
                horariosDisponiveis = listOf(
                    Horario(1, "Segunda-feira", "15:15", true),
                    Horario(2, "Segunda-feira", "15:30", true),
                    Horario(3, "Segunda-feira", "15:45", true),
                    Horario(4, "Segunda-feira", "16:00", true),
                    Horario(5, "Segunda-feira", "16:15", true)
                )
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Branco)
            .verticalScroll(rememberScrollState())
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(RosaClinica)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Logo(modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("AGENDA E HORÁRIOS", fontSize = 16.sp, fontWeight = FontWeight.Light)
                Text(
                    text = dataSelecionada.format(DateTimeFormatter.ofPattern("MMMM yyyy")).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        // Imagem da Sala
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sala),
                contentDescription = "Sala",
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(250.dp)
            )
        }

        // Cabeçalho de Seleção de Data
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(RosaClinica)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("ESCOLHA UMA DATA DE HORÁRIO", fontWeight = FontWeight.Light)
        }

        // Mês/Ano
        Text(
            text = dataSelecionada.format(DateTimeFormatter.ofPattern("MMMM\nyyyy")).uppercase(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Linha do Calendário (Simplificado - 5 dias a partir de hoje)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { dataSelecionada = dataSelecionada.minusDays(1) }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Voltar")
            }

            // Exibe 5 dias a partir da data selecionada
            for (i in 0..4) {
                val data = dataSelecionada.plusDays(i.toLong())
                val isSelected = i == 0 // A primeira é a selecionada visualmente neste exemplo simples

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(width = 50.dp, height = 60.dp)
                        .background(
                            if (isSelected) RosaClinicaMedio else Color.LightGray,
                            RoundedCornerShape(8.dp)
                        )
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { dataSelecionada = data },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${data.format(DateTimeFormatter.ofPattern("EEE")).uppercase().take(3)}\n${data.dayOfMonth}",
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(onClick = { dataSelecionada = dataSelecionada.plusDays(1) }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Avançar")
            }
        }

        // Cabeçalho de Horários
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(RosaClinica)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("HORÁRIOS DISPONÍVEIS", fontWeight = FontWeight.Light)
        }

        // Linha de Horários
        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = RosaClinica)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .horizontalScroll(rememberScrollState()), // Permite rolar horizontalmente se houver muitos
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (horariosDisponiveis.isEmpty()) {
                    Text("Nenhum horário disponível", fontSize = 12.sp)
                } else {
                    horariosDisponiveis.forEach { horario ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    if(horarioSelecionado == horario) RosaClinicaMedio else RosaClinica, // Corrigido: Selecionado fica mais escuro/destacado
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { horarioSelecionado = horario }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(horario.horario, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Informação do Serviço Selecionado
        if (servicoSelecionado != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RosaClinica)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(servicoSelecionado!!.nome)
                Column(horizontalAlignment = Alignment.End) {
                    Text("R$ ${String.format("%.2f", servicoSelecionado!!.preco)}")
                    if (horarioSelecionado != null) {
                        Text("${horarioSelecionado!!.horario}", fontSize = 10.sp)
                    }
                    Text("${servicoSelecionado!!.duracao ?: 60} Min", fontSize = 10.sp)
                }
            }
        } else {
            // Fallback visual se nenhum serviço for encontrado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RosaClinica)
                    .padding(16.dp)
            ) {
                Text("Carregando informações do serviço...", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão Continuar
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            BotaoCustomizado(
                texto = "CONTINUAR",
                aoClicar = {
                    if (servicoSelecionado != null && horarioSelecionado != null) {
                        // Formata data e hora para passar na rota, codificando para URL
                        val dataFormatada = dataSelecionada.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        // Codificando a barra / da data para evitar quebrar a rota
                        val dataUrl = URLEncoder.encode(dataFormatada, StandardCharsets.UTF_8.toString())
                        val horaUrl = URLEncoder.encode(horarioSelecionado!!.horario, StandardCharsets.UTF_8.toString())

                        navController.navigate(Tela.Confirmacao.criarRota(dataFormatada, horarioSelecionado!!.horario))
                    } else {
                        Toast.makeText(context, "Selecione um horário para continuar", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(bottom = 32.dp).width(250.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaAgenda() {
    // Mock para preview
    TelaAgenda(rememberNavController(), 0)
}
