//package org.example.infra.postgres.model;
//
//import java.util.UUID;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//
//@Entity
//public class Order {
//
//  private UUID uuid;
//
//  private String productName;
//
//  @OneToOne
//  @JoinColumn(name = "customer", referencedColumnName = "customer")
//  private Customer customer;
//
//  public UUID getUuid() {
//    return uuid;
//  }
//
//  public void setUuid(final UUID uuid) {
//    this.uuid = uuid;
//  }
//
//  public String getProductName() {
//    return productName;
//  }
//
//  public void setProductName(final String productName) {
//    this.productName = productName;
//  }
//
//  public Customer getCustomer() {
//    return customer;
//  }
//
//  public void setCustomer(final Customer customer) {
//    this.customer = customer;
//  }
//}
