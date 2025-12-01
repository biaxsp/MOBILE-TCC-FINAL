package br.senai.sp.jandira.mobile_maquiagem.service

import br.senai.sp.jandira.mobile_maquiagem.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    // --- CLIENTES ---
    @GET("v1/clientes")
    fun getClientes(): Call<ApiResponse<List<Cliente>>>

    @GET("v1/cliente/{id}")
    fun getClienteById(@Path("id") id: Int): Call<ApiResponse<Cliente>>

    @GET("v1/cliente/{id}/agendamentos")
    fun getClienteComAgendamentosById(@Path("id") id: Int): Call<ApiResponse<Any>> // Ajustar tipo de retorno se tiver modelo complexo

    @POST("v1/cliente")
    fun createCliente(@Body cliente: Cliente): Call<ApiResponse<Cliente>>

    @PUT("v1/cliente/{id}")
    fun updateCliente(@Path("id") id: Int, @Body cliente: Cliente): Call<ApiResponse<Cliente>>

    @DELETE("v1/cliente/{id}")
    fun deleteCliente(@Path("id") id: Int): Call<ApiResponse<Any>>

    // --- SERVIÇOS ---
    // Alteração importante aqui: o retorno do controller para lista de serviços usa a chave "servicos" e não "data"
    // Mas para usar o ApiResponse genérico, vamos manter e adaptar o ApiResponse ou criar um específico.
    // O controller retorna { status, status_code, servicos: [...], message }
    @GET("v1/servicos")
    fun getServicos(): Call<ApiResponseServicesList>

    // O controller retorna { status, status_code, servico: {...}, message }
    @GET("v1/servico/{id}")
    fun getServicoById(@Path("id") id: Int): Call<ApiResponseService>

    // --- PROFISSIONAIS ---
    @GET("v1/profissionais")
    fun getProfissionais(): Call<ApiResponse<List<Profissional>>>

    @GET("v1/profissional/{id}")
    fun getProfissionalById(@Path("id") id: Int): Call<ApiResponse<Profissional>>

    // --- AGENDAMENTOS ---
    @GET("v1/agendamentos")
    fun getAgendamentos(): Call<ApiResponse<List<Agendamento>>>

    @GET("v1/agendamentos/data/{data}")
    fun getAgendamentosPorData(@Path("data") data: String): Call<ApiResponse<List<Agendamento>>>

    @GET("v1/agendamentos/profissional/{id}")
    fun getAgendamentosPorProfissional(@Path("id") id: Int, @Query("data") data: String? = null): Call<ApiResponse<List<Agendamento>>>

    @POST("v1/agendamento")
    fun createAgendamento(@Body agendamento: Agendamento): Call<ApiResponse<Agendamento>>

    @PUT("v1/agendamento/{id}")
    fun updateAgendamento(@Path("id") id: Int, @Body agendamento: Agendamento): Call<ApiResponse<Agendamento>>

    // --- HORÁRIOS ---
    @GET("v1/horarios")
    fun getHorariosDisponiveis(): Call<ApiResponse<List<Horario>>>

    @GET("v1/horarios/dia/{dia}")
    fun getHorariosPorDia(@Path("dia") dia: String): Call<ApiResponse<List<Horario>>>

    @GET("v1/horarios/ativos")
    fun getHorariosAtivos(): Call<ApiResponse<List<Horario>>>
}
