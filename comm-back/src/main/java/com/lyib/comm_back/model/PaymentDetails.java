package com.lyib.comm_back.model;

import lombok.Data;

@Data
public class PaymentDetails {

  private String paymentMethod;

  private String status;

  private String paymentId;

  private String razorpayPaymentLinkId;

  private String razorpayPaymentLinkReferenceId;

  private String razorpayPaymentLinkStatus;

  private String razorpayPaymentId;
}
