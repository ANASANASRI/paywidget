package ma.m2t.paywidget.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.model.Transaction;
import ma.m2t.paywidget.repository.MarchandRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.repository.TransactionRepository;
import ma.m2t.paywidget.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private MarchandRepository marchandRepository;
    private PayMapperImpl dtoMapper;

///****************************************************************************************************
//Post/////////////////////
//    @Override
//    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
//        Transaction customer=dtoMapper.fromTransactionDTO(transactionDTO);
//        Transaction savedCustomer = transactionRepository.save(customer);
//        return dtoMapper.fromTransaction(savedCustomer);
//    }
    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {

        // Obtenez la date actuelle
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formattez la date actuelle dans le format requis
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String formattedDateTime = currentDateTime.format(formatter);

        // Définissez la date formatée comme timestamp dans TransactionDTO
        transactionDTO.setTimestamp(formattedDateTime);
        Transaction transaction = dtoMapper.fromTransactionDTO(transactionDTO);

        // Set PaymentMethod and Marchand if IDs are available
        if (transactionDTO.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(transactionDTO.getPaymentMethodId()).orElse(null);
            transaction.setPaymentMethod(paymentMethod);
        }
        if (transactionDTO.getMarchandId() != null) {
            Marchand marchand = marchandRepository.findById(transactionDTO.getMarchandId()).orElse(null);
            transaction.setMarchand(marchand);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return dtoMapper.fromTransaction(savedTransaction);
    }

///****************************************************************************************************
//Get/////////////////////
    @Override
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<TransactionDTO> transactionDTO=new ArrayList<>();
        for (Transaction t:transactions){
            transactionDTO.add(dtoMapper.fromTransaction(t));
        }
        return transactionDTO;
    }
    @Override
    public List<TransactionDTO> getAllTransactionsByMarchand(Long marchandId) {
        Optional<Marchand> marchand = marchandRepository.findById(marchandId);
        List<Transaction> transactions = transactionRepository.findAllByMarchand(marchand);
        List<TransactionDTO> transactionDTO=new ArrayList<>();
        for (Transaction t:transactions){
            transactionDTO.add(dtoMapper.fromTransaction(t));
        }
        return transactionDTO;
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByMethod(Long methodId) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(methodId);
        List<Transaction> transactions = transactionRepository.findAllByPaymentMethod(paymentMethod);
        List<TransactionDTO> transactionDTO=new ArrayList<>();
        for (Transaction t:transactions){
            transactionDTO.add(dtoMapper.fromTransaction(t));
        }
        return transactionDTO;
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        TransactionDTO transactionDTO = dtoMapper.fromTransaction(transaction);

        return transactionDTO;
    }

    @Override
    public String getTransactionStatus(Long transactionID) {
        Transaction transaction = transactionRepository.findById(transactionID)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        return transaction.getStatus();
    }

    @Override
    public int getNumberOfTransactionsByClientAndMarchand(String clientName, Long marchandId) {
        // Check if clientName and merchantId are not null
        if (clientName == null || marchandId == null) {
            throw new IllegalArgumentException("Client name and merchant ID cannot be null");
        }

        // Retrieve the list of transactions for the given merchant
        List<TransactionDTO> transactions = getAllTransactionsByMarchand(marchandId);

        // Count the number of transactions for the specified client
        long transactionCount = transactions.stream()
                .filter(transaction -> clientName.equals(transaction.getClientName()))
                .count();

        // Convert the count to an integer
        return (int) transactionCount;
    }


///****************************************************************************************************
//Update/////////////////////

    @Override
    public void updateTransactionStatus(Long transactionId, String newStatus) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        // Validate newStatus
        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }

        // Check if the current status is "completed" or "Cancelled"
        if (transaction.getStatus().equalsIgnoreCase("completed")) {
            throw new IllegalStateException("Transaction is already completed and cannot be modified.");
        } else if (transaction.getStatus().equalsIgnoreCase("Cancelled")) {
            if (newStatus.equalsIgnoreCase("completed")) {
                throw new IllegalStateException("Transaction is Cancelled and cannot be completed.");
            }
            throw new IllegalStateException("Transaction is already Cancelled and cannot be modified.");
        }

        // Update the status of the transaction
        transaction.setStatus(newStatus);

        // Save the updated transaction
        transactionRepository.save(transaction);

    }

    private boolean isValidStatus(String status) {
        String[] validStatusValues = {"completed", "pending", "Cancelled"};

        for (String validStatus : validStatusValues) {
            if (validStatus.equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        return null;
    }

///****************************************************************************************************
//Delete/////////////////////
    @Override
    public void deleteTransaction(Long transactionId) {

    }

}