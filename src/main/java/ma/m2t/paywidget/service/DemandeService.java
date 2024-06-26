package ma.m2t.paywidget.service;

import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.dto.PaymentMethodDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import reactor.core.publisher.Flux;

import java.util.List;

public interface DemandeService {
///****************************************************************************************************
//Post
    DemandeDTO saveNewDemande(DemandeDTO demandeDTO);


///*****************************************************************************************************
//Get
    // ( maghatstakhdemch ) katjib li verified o li la
    List<DemandeDTO> getAllDemandes();
    DemandeDTO getDemande(Long demandeId);

    // get (verified=false)
    List<DemandeDTO> getAllDemandesNotVerified();


///****************************************************************************************************
//Update
    // Set (verified=true Accepted=false)
    DemandeDTO UpdateDemandeRejected(Long demandeId) throws MarchandNotFoundException;

    // Set (verified=true Accepted=true)  and  Save merchand
    DemandeDTO UpdateDemandeAccepted(Long demandeId) throws MarchandNotFoundException;

    DemandeDTO UpdateDemandeValuesAndAccepted(Long demandeId,DemandeDTO demandeDTO) throws MarchandNotFoundException;

    DemandeDTO UpdateDemandeValues(Long demandeId,DemandeDTO demandeDTO) throws MarchandNotFoundException;

///****************************************************************************************************
//Delete


///****************************************************************************************************
    /////////////// SEE
    public Flux<DemandeDTO> getAllDemandesNotVerifiedSEE();

}
