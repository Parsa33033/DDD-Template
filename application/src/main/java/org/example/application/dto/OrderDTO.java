package org.example.application.dto;

public class OrderDTO {
  private String identifier;
  private String productName;

  private String customerIdentifier;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(final String identifier) {
    this.identifier = identifier;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(final String productName) {
    this.productName = productName;
  }

  public String getCustomerIdentifier() {
    return customerIdentifier;
  }

  public void setCustomerIdentifier(final String customerIdentifier) {
    this.customerIdentifier = customerIdentifier;
  }
}
