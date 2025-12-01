package br.senai.sp.jandira.mobile_maquiagem.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)

// Novo modelo para a lista de serviços que corresponde ao controller
data class ApiResponseServicesList(
    val status: Boolean,
    val status_code: Int,
    val message: String?,
    val servicos: List<Servico>?
)

// Novo modelo para um único serviço que corresponde ao controller
data class ApiResponseService(
    val status: Boolean,
    val status_code: Int,
    val message: String?,
    val servico: Servico?
)
