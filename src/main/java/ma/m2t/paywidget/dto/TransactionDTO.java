package ma.m2t.paywidget.dto;

import ma.m2t.paywidget.model.Transaction;
import lombok.Data;

@Data
public class TransactionDTO {

    private Long transactionId;
    private String orderId;
    private double amount;
    private String currency;
    private String status;
    private String timestamp; // Changed type to String
    private String clientId;
    private String clientName;
    private String clientEmail;
    private String hmac;
    private String notif;
    private Long paymentMethodId;
    private Long merchantId;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.orderId = transaction.getOrderId();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.status = transaction.getStatus();
        this.timestamp = transaction.getTimestamp();
        this.clientId = transaction.getClientId();
        this.clientName = transaction.getClientName();
        this.clientEmail = transaction.getClientEmail();
        this.hmac = transaction.getHmac();
        this.notif = transaction.getNotif();
        if (transaction.getPaymentMethod() != null) {
            this.paymentMethodId = transaction.getPaymentMethod().getPaymentMethodId();
        }
        if (transaction.getMerchant() != null) {
            this.merchantId = transaction.getMerchant().getMerchantId();
        }
    }
}

