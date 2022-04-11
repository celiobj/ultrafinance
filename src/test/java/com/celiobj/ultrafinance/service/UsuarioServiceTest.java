package com.celiobj.ultrafinance.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.celiobj.ultrafinance.exception.RegraNegocioException;
import com.celiobj.ultrafinance.model.entity.Usuario;
import com.celiobj.ultrafinance.model.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveValidarEmail() {

		// cenario
		repository.deleteAll();

		// acao
		service.validarEmail("email@email.com");

	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		// cenario
		repository.deleteAll();

		// acao
		Exception exception = assertThrows(RegraNegocioException.class, () -> {
			Usuario usuario = Usuario.builder().nome("Usuario").email("email@email.com").build();
			repository.save(usuario);
			service.validarEmail("email@email.com");
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Já existe um usuário cadastrado com este e-mail"));

	}

}
