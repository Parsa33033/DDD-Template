package org.example.infra.sql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_c")
public class Order {

  @Id
  @Column(name = "order_id")
  private String orderId;

  private String productName;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
  private Customer customer;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(final String orderId) {
    this.orderId = orderId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(final String productName) {
    this.productName = productName;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }
}
