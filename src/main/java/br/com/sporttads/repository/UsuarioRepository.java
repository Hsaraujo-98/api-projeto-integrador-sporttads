package br.com.sporttads.repository;


import br.com.sporttads.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    @Query("select u from UsuarioModel u where u.email like :email")
    UsuarioModel findByEmail(@Param("email") String email);
}