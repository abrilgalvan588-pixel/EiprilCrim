package com.eiprilcrim.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eiprilcrim.domain.model.Usuario
import com.eiprilcrim.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioState())
    val uiState: StateFlow<UsuarioState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }

    fun onCorreoChange(correo: String) {
        _uiState.update { it.copy(correo = correo) }
    }

    fun onContrasenaChange(contrasena: String) {
        _uiState.update { it.copy(contrasena = contrasena) }
    }

    fun toggleModoRegistro() {
        _uiState.update {
            it.copy(
                esRegistro = !it.esRegistro,
                mensaje = ""
            )
        }
    }

    fun iniciarSesion(
        textoCamposVacios: String,
        textoDatosIncorrectos: String,
        onExito: (Usuario) -> Unit
    ) {
        val currentState = _uiState.value
        if (currentState.correo.isBlank() || currentState.contrasena.isBlank()) {
            _uiState.update { it.copy(mensaje = textoCamposVacios) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensaje = "") }
            val usuario = usuarioRepository.obtenerPorCorreo(currentState.correo.trim())
            if (usuario != null && usuario.contrasena == currentState.contrasena) {
                _uiState.update { it.copy(usuarioActual = usuario, isLoading = false) }
                onExito(usuario)
            } else {
                _uiState.update { it.copy(mensaje = textoDatosIncorrectos, isLoading = false) }
            }
        }
    }

    fun registrarse(
        textoCamposVacios: String,
        textoCorreoExistente: String,
        textoRegistroExitoso: String
    ) {
        val currentState = _uiState.value
        if (currentState.nombre.isBlank() || currentState.correo.isBlank() || currentState.contrasena.isBlank()) {
            _uiState.update { it.copy(mensaje = textoCamposVacios) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensaje = "") }
            val existente = usuarioRepository.obtenerPorCorreo(currentState.correo.trim())
            if (existente != null) {
                _uiState.update { it.copy(mensaje = textoCorreoExistente, isLoading = false) }
            } else {
                val nuevoUsuario = Usuario(
                    nombre = currentState.nombre.trim(),
                    correo = currentState.correo.trim(),
                    contrasena = currentState.contrasena
                )
                usuarioRepository.insertarUsuario(nuevoUsuario)
                _uiState.update {
                    it.copy(
                        mensaje = textoRegistroExitoso,
                        esRegistro = false,
                        nombre = "",
                        contrasena = "",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun cerrarSesion() {
        _uiState.update {
            UsuarioState()
        }
    }

    class Factory(
        private val usuarioRepository: UsuarioRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
                return UsuarioViewModel(usuarioRepository) as T
            }
            throw IllegalArgumentException("Clase ViewModel desconocida")
        }
    }
}
