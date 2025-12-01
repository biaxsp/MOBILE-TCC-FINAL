package br.senai.sp.jandira.mobile_maquiagem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.RosaBotao
import br.senai.sp.jandira.mobile_maquiagem.ui.theme.TextoEscuro

@Composable
fun BotaoCustomizado(
    texto: String,
    aoClicar: () -> Unit,
    modifier: Modifier = Modifier,
    corContainer: Color = RosaBotao,
    corTexto: Color = TextoEscuro
) {
    Button(
        onClick = aoClicar,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = corContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = texto,
            fontSize = 18.sp,
            color = corTexto
        )
    }
}
