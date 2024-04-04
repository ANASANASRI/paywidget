package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.service.TransactionService;
import org.springframework.web.bind.annotation.*;

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



}
