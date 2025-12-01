package br.senai.sp.jandira.mobile_maquiagem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.jandira.mobile_maquiagem.model.Tela
import br.senai.sp.jandira.mobile_maquiagem.screens.TelaAgenda
import br.senai.sp.jandira.mobile_maquiagem.screens.TelaConfirmacao
import br.senai.sp.jandira.mobile_maquiagem.screens.TelaInicio
import br.senai.sp.jandira.mobile_maquiagem.screens.TelaServicos
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.Mobile_maquiagemTheme
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobile_maquiagemTheme {
                NavegacaoApp()
            }
        }
    }
}

@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Tela.Inicio.rota) {
        composable(Tela.Inicio.rota) {
            TelaInicio(navController)
        }
        composable(Tela.Servicos.rota) {
            TelaServicos(navController)
        }
        composable(
            route = Tela.Agenda.rota,
            arguments = listOf(navArgument("servicoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val servicoId = backStackEntry.arguments?.getInt("servicoId") ?: 0
            TelaAgenda(navController, servicoId)
        }
        composable(
            route = Tela.Confirmacao.rota,
            arguments = listOf(
                navArgument("data") { type = NavType.StringType },
                navArgument("hora") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val dataEncoded = backStackEntry.arguments?.getString("data") ?: ""
            val horaEncoded = backStackEntry.arguments?.getString("hora") ?: ""

            // Decodificar os parâmetros para remover os caracteres de URL (ex: %2F vira /)
            val data = try {
                URLDecoder.decode(dataEncoded, StandardCharsets.UTF_8.toString())
            } catch (e: Exception) {
                dataEncoded
            }

            val hora = try {
                URLDecoder.decode(horaEncoded, StandardCharsets.UTF_8.toString())
            } catch (e: Exception) {
                horaEncoded
            }

            TelaConfirmacao(navController, data, hora)
        }
    }
}
