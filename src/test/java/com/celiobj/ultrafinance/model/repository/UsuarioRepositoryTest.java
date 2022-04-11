package com.celiobj.ultrafinance.model.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.celiobj.ultrafinance.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") //
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repositoriy;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenário
		Usuario usuario =CriarUsuario();
		entityManager.persist(usuario);

		// Ação
		boolean result = repositoriy.existsByEmail("usuario@email.com");

		// Verificação
		Assertions.assertTrue(result);
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// cenário

		// Ação
		boolean result = repositoriy.existsByEmail("usuario@email.com");

		// Verificação
		Assertions.assertFalse(result);
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {

		// cenário
		Usuario usuario = CriarUsuario();

		// acao
		Usuario usuarioSalvo = repositoriy.save(usuario);

		// verificacao
		Assertions.assertNotNull(usuarioSalvo.getId());
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		
		//cenario
		Usuario usuario = CriarUsuario();
		entityManager.persist(usuario);
		
		//acao
		Optional<Usuario> result = repositoriy.findByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertNotNull(result.isPresent());
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoEisteNaBase() {
		
		//cenario
		
		//acao
		Optional<Usuario> result = repositoriy.findByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertNotNull(result.isEmpty());
	}

	public static Usuario CriarUsuario() {
		Usuario usuario = Usuario
				.builder()
				.nome("Usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
		return usuario;
	}
}
