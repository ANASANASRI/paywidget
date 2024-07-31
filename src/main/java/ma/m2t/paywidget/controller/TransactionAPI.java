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

    @GetMapping("/byMarchand/{marchandId}")
    public List<TransactionDTO> getAllTransactionsByMarchand(@PathVariable Long marchandId) {
        return transactionService.getAllTransactionsByMarchand(marchandId);
    }

    @GetMapping("/byPaymentMethod/{paymentmethod}")
    public List<TransactionDTO> getAllTransactionsByMethod(@PathVariable Long paymentmethod) {
        return transactionService.getAllTransactionsByMethod(paymentmethod);
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @GetMapping("/status/{transactionId}")
    public String getTransactionStatus(@PathVariable Long transactionId) {
        return transactionService.getTransactionStatus(transactionId);
    }

    @GetMapping("/NumberOfTransactionsByClientAndMarchantd/{marchandId}/{clientName}")
    public int getNumberOfTransactionsByClientAndMarchand(@PathVariable Long marchandId, @PathVariable String clientName) {
        return transactionService.getNumberOfTransactionsByClientAndMarchand(clientName, marchandId);
    }

//UPDATE

    @PutMapping("/changestatus/{transactionId}/{newstatus}")
    public void updateTransactionStatus(@PathVariable Long transactionId,@PathVariable String newstatus) {
        transactionService.updateTransactionStatus(transactionId,newstatus);
    }

//DELETE

}
