package com.celiobj.ultrafinance.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.celiobj.ultrafinance.exception.RegraNegocioException;
import com.celiobj.ultrafinance.model.entity.Usuario;
import com.celiobj.ultrafinance.model.repository.UsuarioRepository;
import com.celiobj.ultrafinance.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioServiceTest {

	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;

	@Before
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
	}

	@Test
	public void deveValidarEmail() {

		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// acao
		service.validarEmail("email@email.com");

	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// acao
		Exception exception = assertThrows(RegraNegocioException.class, () -> {
			Usuario usuario = Usuario.builder().nome("Usuario").email("email@email.com").build();
			repository.save(usuario);
			service.validarEmail("email@email.com");
		});

		assertTrue(exception.getMessage().equalsIgnoreCase("Já existe um usuário cadastrado com este e-mail"));

	}

}
