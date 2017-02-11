package br.com.rbarbioni.bluebank.repository;

import br.com.rbarbioni.bluebank.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by renan on 10/02/2017.
 */
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    @Query("SELECT c FROM ContaCorrente c WHERE conta.cpf=?1 AND conta.agencia=?2 AND conta.conta=?3")
    ContaCorrente findUnique(ContaCorrente conta);
}
