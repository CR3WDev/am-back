package dev.am.am.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.am.am.models.BillModel;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<BillModel, UUID> {

    @Query("SELECT b FROM BillModel b WHERE b.user.id = :userId")
    List<BillModel> findAllByUserId(UUID userId);

}
