package br.com.alura.forum.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.forum.modelo.Curso;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Comando para não substituir o banco de
																				// dados pelo H2(Banco de Dados padrão
																				// que o spring utiliza para teste).
@ActiveProfiles("test")
public class CursoRepositoryTest {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TestEntityManager em;

	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "Spring Boot";

		Curso cursoSpringBoot = new Curso();
		cursoSpringBoot.setNome(nomeCurso);
		cursoSpringBoot.setCategoria("Programação");
		em.persist(cursoSpringBoot);

		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assertions.assertNotNull(curso);
		Assertions.assertEquals(nomeCurso, curso.getNome());
	}

	@Test
	public void naoDeveriaCarregarUmCursoCujoNomeNaoEstaCadastrado() {
		String nomeCurso = "Spring Bo";

		Curso curso = cursoRepository.findByNome(nomeCurso);
		Assertions.assertNull(curso);
	}

}
