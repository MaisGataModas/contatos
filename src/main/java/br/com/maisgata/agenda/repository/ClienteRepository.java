package br.com.maisgata.agenda.repository;

import br.com.maisgata.agenda.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
