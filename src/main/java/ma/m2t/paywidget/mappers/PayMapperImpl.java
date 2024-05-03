package ma.m2t.paywidget.mappers;

import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.dto.TransactionDTO;
import ma.m2t.paywidget.model.Demande;
import ma.m2t.paywidget.model.Marchand;
import ma.m2t.paywidget.model.PaymentMethod;
import ma.m2t.paywidget.model.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PayMapperImpl {

    //Transaction
    public TransactionDTO fromTransaction(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);

        // Set PaymentMethod and Marchand IDs if available
        if (transaction.getPaymentMethod() != null) {
            transactionDTO.setPaymentMethodId(transaction.getPaymentMethod().getPaymentMethodId());
        }
        if (transaction.getMarchand() != null) {
            transactionDTO.setMarchandId(transaction.getMarchand().getMarchandId());
        }

        return transactionDTO;
    }


    public TransactionDTO fromTransactionBasic(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);

        return transactionDTO;
    }

    public Transaction fromTransactionDTO(TransactionDTO transactionDTO){
        Transaction transaction=new Transaction();
        BeanUtils.copyProperties(transactionDTO,transaction);
        return  transaction;
    }

    //Marchand
    public MarchandDTO fromMarchand(Marchand marchand){
        MarchandDTO marchandDTO =new MarchandDTO();
        BeanUtils.copyProperties(marchand, marchandDTO);
        return marchandDTO;
    }

    public Marchand fromMarchandDTO(MarchandDTO marchandDTO){
        Marchand marchand =new Marchand();
        BeanUtils.copyProperties(marchandDTO, marchand);
        return marchand;
    }


    //Demande
    public DemandeDTO fromDemande(Demande demande){
        DemandeDTO demandeDTO =new DemandeDTO();
        BeanUtils.copyProperties(demande, demandeDTO);
        return demandeDTO;
    }

    public Demande fromDemandeDTO(DemandeDTO demandeDTO){
        Demande demande =new Demande();
        BeanUtils.copyProperties(demandeDTO, demande);
        return demande;
    }

    //Payment

    public PaymentMethodDTO fromPaymentMethod(PaymentMethod paymentMethod){
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
        BeanUtils.copyProperties(paymentMethod, paymentMethodDTO);
        return  paymentMethodDTO;
    }

    public PaymentMethod fromPaymentMethodDTO(PaymentMethodDTO paymentMethodDTO){
        PaymentMethod paymentMethod = new PaymentMethod();
        BeanUtils.copyProperties(paymentMethodDTO, paymentMethod);
        return  paymentMethod;
    }


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