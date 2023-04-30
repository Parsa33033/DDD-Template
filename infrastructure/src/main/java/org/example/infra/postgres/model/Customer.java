package org.example.infra.postgres.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @Column(name = "customer_id")
  private UUID customerId;

  private String name;

  @OneToMany(mappedBy = "customer")
  private Set<Order> orders;

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(final UUID customerId) {
    this.customerId = customerId;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(final Set<Order> orders) {
    this.orders = orders;
  }
}
