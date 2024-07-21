package br.com.futurodev.tasksapibasicsecurity.repository;

import br.com.futurodev.tasksapibasicsecurity.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByDeletedFalse(Pageable pageable);

    Optional<Task> findByIdAndDeletedFalse(Long id);

    Optional<Task> findByIdAndDeletedFalseAndOwnerId(Long id, Long ownerId);

    Page<Task> findDistinctByDeletedFalseAndOwnerIdOrAssigneesId(Pageable pageable, Long ownerId, Long assigneeId);
}
