package ma.m2t.paywidget.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Merchant;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.model.Transaction;
import ma.m2t.paywidget.repository.MerchantMethodsRepository;
import ma.m2t.paywidget.repository.MerchantRepository;
import ma.m2t.paywidget.repository.PaymentMethodRepository;
import ma.m2t.paywidget.repository.TransactionRepository;
import ma.m2t.paywidget.service.TransactionService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
    private MerchantRepository merchantRepository;
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

        // Set PaymentMethod and Merchant if IDs are available
        if (transactionDTO.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(transactionDTO.getPaymentMethodId()).orElse(null);
            transaction.setPaymentMethod(paymentMethod);
        }
        if (transactionDTO.getMerchantId() != null) {
            Merchant merchant = merchantRepository.findById(transactionDTO.getMerchantId()).orElse(null);
            transaction.setMerchant(merchant);
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
    public List<TransactionDTO> getAllTransactionsByMerchant(Long merchantId) {
        Optional<Merchant> merchant = merchantRepository.findById(merchantId);
        List<Transaction> transactions = transactionRepository.findAllByMerchant(merchant);
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
        return null;
    }
///****************************************************************************************************
//Update/////////////////////
    @Override
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        return null;
    }

    @Override
    public void updateTransactionStatus(Long transactionId) {

    }
///****************************************************************************************************
//Delete/////////////////////
    @Override
    public void deleteTransaction(Long transactionId) {

    }

}
