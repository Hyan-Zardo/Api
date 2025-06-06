package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID>, PurchaseOrderRepositoryCustom {

}
