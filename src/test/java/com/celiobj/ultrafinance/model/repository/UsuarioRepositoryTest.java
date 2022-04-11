package com.celiobj.ultrafinance.model.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.celiobj.ultrafinance.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repositoriy;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("usuariotrue").email("usuariotrue@email.com").build();
		repositoriy.save(usuario);
		
		//Ação
		boolean result = repositoriy.existsByEmail("usuariotrue@email.com");
		
		//Verificação
		Assertions.assertTrue(result);
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("usuariofalse").email("usuariofalse@email.com").build();
		repositoriy.save(usuario);
		
		//Ação
		boolean result = repositoriy.existsByEmail("usuariofalsediferente@email.com");
		
		//Verificação
		Assertions.assertFalse(result);
	}

}
