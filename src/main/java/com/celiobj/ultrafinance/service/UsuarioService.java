package com.celiobj.ultrafinance.service;

import com.celiobj.ultrafinance.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);
}
