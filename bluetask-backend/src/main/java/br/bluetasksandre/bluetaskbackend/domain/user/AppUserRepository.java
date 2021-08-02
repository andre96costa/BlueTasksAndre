package br.bluetasksandre.bluetaskbackend.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false) //A notação do Spring Data Rest, permite controlar a exposição do repositorio, nesse caso estamos escondendo o repositorio
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    AppUser findByUsername(String username);
}
