package br.com.rbarbioni.bluebank.repository;

import br.com.rbarbioni.bluebank.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by renan on 10/02/2017.
 */
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    @Query("SELECT c FROM #{#entityName} c WHERE c.cpf=?1 AND c.agencia=?2 AND c.conta=?3")
    ContaCorrente findUnique(String cpf, String agencia, String conta);
}
