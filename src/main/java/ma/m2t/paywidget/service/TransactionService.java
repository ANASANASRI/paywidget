package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
///****************************************************************************************************
//Post
    TransactionDTO saveTransaction(TransactionDTO transactionDTO);

///****************************************************************************************************
//Get
    List<TransactionDTO> getAllTransactions();
    List<TransactionDTO> getAllTransactionsByMarchand(Long marchand);
    List<TransactionDTO> getAllTransactionsByMethod(Long methodId);
    TransactionDTO getTransactionById(Long transactionId);
    String getTransactionStatus(Long transactionID);
    int getNumberOfTransactionsByClientAndMarchand(String clientName, Long marchandId);
///****************************************************************************************************
//Update
    TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    void updateTransactionStatus(Long transactionId , String newStatus);

///****************************************************************************************************
//Delete
    void deleteTransaction(Long transactionId);
}
///****************************************************************************************************
//Post

///****************************************************************************************************
//Get

///****************************************************************************************************
//Update

///****************************************************************************************************
//Delete