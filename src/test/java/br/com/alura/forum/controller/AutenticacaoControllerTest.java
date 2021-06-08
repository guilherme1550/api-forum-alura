package br.com.alura.forum.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest //Carrega todas as classes necessárias do spring.
@AutoConfigureMockMvc //Necessário para ser possível injetar o mockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveriaRetornarBadRequestAoColocarUsuárioESenhaIncorretos() throws Exception {
		URI uri = new URI("/auth");
		String json = "{\"email\":\"aluno@email.com\",\"senha\":\"12345\"}";
		System.out.println(json);
		
		mockMvc
		.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.is(400));
	}

}
