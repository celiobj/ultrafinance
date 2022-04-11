package com.celiobj.ultrafinance.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.celiobj.ultrafinance.exception.ErroAutenticacaoException;
import com.celiobj.ultrafinance.exception.RegraNegocioException;
import com.celiobj.ultrafinance.model.entity.Usuario;
import com.celiobj.ultrafinance.model.repository.UsuarioRepository;
import com.celiobj.ultrafinance.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if (!usuario.isPresent()) {
			throw new ErroAutenticacaoException("Usuário não encontrato para o e-mail informado");
		}

		if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoException("Senha inválida");
		}

		return usuario.get();
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);

		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
		}

	}

}
