package ma.m2t.paywidget.mappers;

import ma.m2t.paywidget.dto.MerchantDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.model.Merchant;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.model.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PayMapperImpl {
    
    //Transaction
    public TransactionDTO fromTransaction(Transaction transaction){
        TransactionDTO transactionDTO=new TransactionDTO();
        BeanUtils.copyProperties(transaction,transactionDTO);
        return  transactionDTO;
    }

    public Transaction fromTransactionDTO(TransactionDTO transactionDTO){
        Transaction transaction=new Transaction();
        BeanUtils.copyProperties(transactionDTO,transaction);
        return  transaction;
    }

    //Merchant
    public MerchantDTO fromMerchant(Merchant merchant){
        MerchantDTO merchantDTO=new MerchantDTO();
        BeanUtils.copyProperties(merchant,merchantDTO);
        return  merchantDTO;
    }

    public Merchant fromMerchantDTO(MerchantDTO merchantDTO){
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDTO,merchant);
        return  merchant;
    }

    //Admin


//    public XXXXXDTO fromXXXXX(XXXXX xxxxx){
//        XXXXXDTO xxxxxDTO=new XXXXXDTO();
//        BeanUtils.copyProperties(xxxxx,xxxxxDTO);
//        return  xxxxxDTO;
//    }
//
//    public XXXXX fromXXXXXDTO(XXXXXDTO xxxxxDTO){
//        XXXXX xxxxx=new XXXXX();
//        BeanUtils.copyProperties(xxxxxDTO,xxxxx);
//        return  xxxxx;
//    }
//
}
