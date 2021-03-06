package br.com.sporttads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sporttads.model.FreteModel;

@Repository
public interface FreteRepository extends JpaRepository<FreteModel, Integer> {

}
