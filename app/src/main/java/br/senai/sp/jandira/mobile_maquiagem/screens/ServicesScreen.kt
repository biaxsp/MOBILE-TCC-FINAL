package br.senai.sp.jandira.mobile_maquiagem.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import br.senai.sp.jandira.mobile_maquiagem.model.ApiResponse
import br.senai.sp.jandira.mobile_maquiagem.model.ApiResponseServicesList
import br.senai.sp.jandira.mobile_maquiagem.model.Servico
import br.senai.sp.jandira.mobile_maquiagem.model.Tela
import br.senai.sp.jandira.mobile_maquiagem.model.listaServicos
import br.senai.sp.jandira.mobile_maquiagem.service.RetrofitFactory
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.Branco
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaClinica
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun TelaServicos(navController: NavController) {

    // Estado para armazenar a lista de serviços vinda da API
    var servicosState by remember { mutableStateOf<List<Servico>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Efeito para carregar os serviços ao abrir a tela
    LaunchedEffect(Unit) {
        val call = RetrofitFactory.userService.getServicos()
        call.enqueue(object : Callback<ApiResponseServicesList> {
            override fun onResponse(
                call: Call<ApiResponseServicesList>,
                response: Response<ApiResponseServicesList>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.servicos != null) {
                        servicosState = apiResponse.servicos
                    } else {
                        Log.e("API", "Resposta vazia ou sem dados")
                        servicosState = listaServicos
                    }
                } else {
                    Log.e("API", "Erro na resposta: ${response.code()}")
                    servicosState = listaServicos
                }
                isLoading = false
            }

            override fun onFailure(call: Call<ApiResponseServicesList>, t: Throwable) {
                Log.e("API", "Falha na requisição: ${t.message}")
                servicosState = listaServicos
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
        // Barra Superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(RosaClinica)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Logo(modifier = Modifier.size(60.dp))
            IconButton(onClick = { /* TODO: Abrir Menu */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Imagem da Recepção
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.recepcao),
                contentDescription = "Recepção",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Texto de Descrição
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(RosaClinica)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "A MyBIA nasceu através do propósito de inspirar as pessoas a despertarem a sua Real Beleza através de um atendimento pautado no amor",
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "É dessa forma que trabalhamos para transformar a autoestima de nossas clientes.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grade de Serviços
        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = RosaClinica)
            }
        } else {
            val servicosEmBlocos = servicosState.chunked(3)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                servicosEmBlocos.forEach { itensLinha ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        itensLinha.forEach { servico ->
                            ItemServico(servico = servico, aoClicar = {
                                navController.navigate(Tela.Agenda.criarRota(servico.id))
                            })
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão genérico de Reservar
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            BotaoCustomizado(
                texto = "Reservar horário",
                aoClicar = { navController.navigate(Tela.Agenda.criarRota(0)) },
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun ItemServico(servico: Servico, aoClicar: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp)
            .clickable { aoClicar() } // Adicionado clique
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            // Lógica de fallback de imagem:
            // 1. Se tiver imagemRes local (mock/fixo), usa.
            // 2. Se não, usa um placeholder (IMG).
            // Como a API não retorna URL de imagem por enquanto, isso é esperado.
            // Para corrigir visualmente, podemos tentar mapear IDs conhecidos para imagens locais.

            val imagemFinal = when {
                servico.imagemRes != null -> servico.imagemRes
                servico.nome.contains("Russo", ignoreCase = true) -> R.drawable.cilios1
                servico.nome.contains("Fio a Fio", ignoreCase = true) -> R.drawable.cilios2
                servico.nome.contains("Brasileiro", ignoreCase = true) -> R.drawable.cilios3
                servico.nome.contains("Molhado", ignoreCase = true) -> R.drawable.cilios4
                servico.nome.contains("Híbrido", ignoreCase = true) || servico.nome.contains("Hibrido", ignoreCase = true) -> R.drawable.cilios5
                servico.nome.contains("Egípcio", ignoreCase = true) || servico.nome.contains("Egipcio", ignoreCase = true) -> R.drawable.cilios6
                else -> null
            }

            if (imagemFinal != null) {
                Image(
                    painter = painterResource(id = imagemFinal),
                    contentDescription = servico.nome,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("IMG", fontSize = 10.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = servico.nome,
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2
        )
        Text(
            text = String.format("R$ %.2f", servico.preco),
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaServicos() {
    TelaServicos(rememberNavController())
}
