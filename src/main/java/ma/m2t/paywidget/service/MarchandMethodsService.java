package ma.m2t.paywidget.service;

import ma.m2t.paywidget.exceptions.MarchandNotFoundException;

import java.util.List;
import java.util.Map;

public interface MarchandMethodsService {
///****************************************************************************************************
//Post/////////////////////

///****************************************************************************************************
//Get/////////////////////
    List<Map<String, Object>> getMarchandPaymentMethods(Long marchandId) throws MarchandNotFoundException;


///****************************************************************************************************
//Update/////////////////////
    void selectPaymentMethodInMarchand(Long marchandId, Long paymentMethodId) throws MarchandNotFoundException;

///****************************************************************************************************
//Delete/////////////////////


////////////////////////////////////////

    boolean findStatusMarchandPaymentByMarchandIdAndPaymentMethodId(Long marchandId, Long paymentMethodId);


}
