package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.entities.RentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentOrderRepository extends JpaRepository<RentOrder, UUID>, RentOrderRepositoryCustom {

}
