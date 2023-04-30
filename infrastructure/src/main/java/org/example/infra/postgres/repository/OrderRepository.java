package org.example.infra.postgres.repository;

import java.util.UUID;
import org.example.infra.postgres.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
