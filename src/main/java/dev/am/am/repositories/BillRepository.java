package dev.am.am.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.am.am.models.BillModel;

public interface BillRepository extends JpaRepository<BillModel, UUID> {

}
