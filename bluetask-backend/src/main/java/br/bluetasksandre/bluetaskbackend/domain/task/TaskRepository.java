package br.bluetasksandre.bluetaskbackend.domain.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

    Task findByDescription(String description);

    @Override
    @Query("SELECT t FROM Task t WHERE t.appUser.username = ?#{principal}")
    Page<Task> findAll(Pageable pageable);

    @Override
    @Query("SELECT t from Task t WHERE t.id = ?1 AND t.appUser.username = ?#{principal}")
    Optional<Task> findById(Integer integer);
}
