package br.com.alura.forum.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao); //Esse método faz o mesmo que o método procuraPorNomeDoCurso
	
//	@Query("SELECT t FROM Topico t WHERE curso.nome = :nomeCurso")
//	public List<Topico> procuraPorNomeDoCurso(@Param("nomeCurso") String nomeCurso); //Posso colocar o @Param ou utilizar sem.
	
	
}
