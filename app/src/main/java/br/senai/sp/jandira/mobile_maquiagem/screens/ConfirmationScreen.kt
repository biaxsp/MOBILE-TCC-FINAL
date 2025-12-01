package br.senai.sp.jandira.mobile_maquiagem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.mobile_maquiagem.components.BotaoCustomizado
import br.senai.sp.jandira.mobile_maquiagem.components.Logo
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaClinica
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.Branco
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaBotaoMaisEscuro

@Composable
fun TelaConfirmacao(navController: NavController, data: String = "OUT 21, 2025", hora: String = "17:00") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Branco),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Seção superior rosa com logotipo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(RosaClinica),
            contentAlignment = Alignment.Center
        ) {
            Logo()
        }

        // Seção inferior branca
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Ícone de Verificação Circular
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, CircleShape)
                    .border(4.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Sucesso",
                    modifier = Modifier.size(60.dp),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "AGENDAMENTO CONFIRMADO",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            // Exibe a data e hora recebidos por parâmetro
            Text(
                text = "$data, $hora",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CONCLUÍDO! VAMOS ENVIAR UM LEMBRETE ANTES DO SEU AGENDAMENTO.",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(60.dp))

            BotaoCustomizado(
                texto = "VOLTAR",
                aoClicar = { navController.popBackStack(br.senai.sp.jandira.mobile_maquiagem.model.Tela.Inicio.rota, inclusive = false) },
                corContainer = RosaBotaoMaisEscuro
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaConfirmacao() {
    TelaConfirmacao(rememberNavController())
}
