package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    //Post
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);

    //Get
    List<TransactionDTO> getAllTransactionsByMerchant(Long merchantId);
    List<TransactionDTO> getAllTransactionsByMethod(Long methodId);
    TransactionDTO getTransactionById(Long transactionId);
    String getTransactionStatus(Long transactionID);

    //Update
    TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    void updateTransactionStatus(Long transactionId);

    //Delete
    void deleteTransaction(Long transactionId);

}
