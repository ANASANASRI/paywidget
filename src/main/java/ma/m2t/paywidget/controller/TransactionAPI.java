package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("transaction")
public class TransactionAPI {

private TransactionService transactionService;

//POST
    @PostMapping("/save")
    public TransactionDTO saveTransaction(@RequestBody TransactionDTO transactionDTO){
        this.transactionService.saveTransaction(transactionDTO);
        return transactionDTO;
    }

//GET
    @GetMapping("/all")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/byMerchant")
    public List<TransactionDTO> getAllTransactionsByMerchant(@RequestParam Long merchantId) {
        return transactionService.getAllTransactionsByMerchant(merchantId);
    }

    @GetMapping("/byPaymentMethod")
    public List<TransactionDTO> getAllTransactionsByMethod(@RequestParam Long paymentmethod) {
        return transactionService.getAllTransactionsByMethod(paymentmethod);
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

//UPDATE

//DELETE

}
