package br.senai.sp.jandira.mobile_maquiagem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.mobile_maquiagem.components.BotaoCustomizado
import br.senai.sp.jandira.mobile_maquiagem.components.Logo
import br.senai.sp.jandira.mobile_maquiagem.model.Tela
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaClinica
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.Branco

@Composable
fun TelaInicio(navController: NavController) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "AGENDE SEU HORÁRIO\nONLINE",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            BotaoCustomizado(
                texto = "Agendar",
                aoClicar = { navController.navigate(Tela.Servicos.rota) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTelaInicio() {
    TelaInicio(rememberNavController())
}
