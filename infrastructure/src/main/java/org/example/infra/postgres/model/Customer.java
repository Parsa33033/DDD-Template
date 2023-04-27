//package org.example.infra.postgres.model;
//
//import java.util.Set;
//import java.util.UUID;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
//@Entity
//public class Customer {
//
//  @Id
//  private UUID uuid;
//
//  private String name;
//
//  @OneToMany(mappedBy = "customer")
//  private Set<Order> orders;
//
//  public UUID getUuid() {
//    return uuid;
//  }
//
//  public void setUuid(final UUID uuid) {
//    this.uuid = uuid;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(final String name) {
//    this.name = name;
//  }
//
//  public Set<Order> getOrders() {
//    return orders;
//  }
//
//  public void setOrders(final Set<Order> orders) {
//    this.orders = orders;
//  }
//}
