package ma.m2t.paywidget.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.emailing.EmailService;
import ma.m2t.paywidget.enums.Status;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.model.Demande;
import ma.m2t.paywidget.repository.DemandeRepository;
import ma.m2t.paywidget.service.DemandeService;
import ma.m2t.paywidget.service.MarchandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("demandes")
public class DemandeApi {
    private final DemandeService demandeService;
    private DemandeRepository demandeRepository;
    private MarchandService marchandService;
    private EmailService emailService;

    @PostMapping
    @Transactional
    public ResponseEntity<DemandeDTO> saveNewDemande(@RequestBody DemandeDTO demandeDTO) throws MessagingException {
        DemandeDTO savedDemande = demandeService.saveNewDemande(demandeDTO);
        this.emailService.sendDemandeVerificationEmail(demandeDTO.getDemandeMarchandEmail());
        return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<DemandeDTO>> getAllDemandes() {
        List<DemandeDTO> demandes = demandeService.getAllDemandes();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/demande/{demandeId}")
    public ResponseEntity<DemandeDTO> getDemande(@PathVariable Long demandeId) {
        DemandeDTO demandeDto = demandeService.getDemande(demandeId);
        return new ResponseEntity<>(demandeDto, HttpStatus.OK);
    }

    @GetMapping("/not-verified")
    public ResponseEntity<List<DemandeDTO>> getAllDemandesNotVerified() {
        List<DemandeDTO> demandes = demandeService.getAllDemandesNotVerified();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @PutMapping("/{demandeId}/rejected")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemandeRejected(@PathVariable Long demandeId) throws MarchandNotFoundException, MessagingException {
        DemandeDTO updatedDemande = demandeService.UpdateDemandeRejected(demandeId);

        Optional<Demande> demandeOptional = demandeRepository.findById(demandeId);

        //email
        if (demandeOptional.isPresent()) {
            Demande demande = demandeOptional.get();
            this.emailService.sendValidationRejectedEmail(demande.getDemandeMarchandEmail());
        } else {
            throw new MarchandNotFoundException("Demande with ID " + demandeId + " not found");
        }

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }

    @PutMapping("/{demandeId}/accepted")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemandeAccepted(@PathVariable Long demandeId) throws MarchandNotFoundException, MessagingException {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeAccepted(demandeId);


        //
        MarchandDTO marchandDTO = new MarchandDTO();
        marchandDTO.setMarchandEmail(updatedDemande.getDemandeMarchandEmail());
        marchandDTO.setMarchandName(updatedDemande.getDemandeMarchandName());
        marchandDTO.setMarchandDescription(updatedDemande.getDemandeMarchandDescription());
        marchandDTO.setMarchandPhone(updatedDemande.getDemandeMarchandPhone());
        marchandDTO.setMarchandHost(updatedDemande.getDemandeMarchandHost());
        marchandDTO.setMarchandLogoUrl(updatedDemande.getDemandeMarchandLogoUrl());
        marchandDTO.setMarchandTypeActivite(updatedDemande.getDemandeMarchandTypeActivite());
        marchandDTO.setMarchandRcIf(updatedDemande.getDemandeMarchandRcIf());
        marchandDTO.setMarchandSiegeAddresse(updatedDemande.getDemandeMarchandSiegeAddresse());
        marchandDTO.setMarchandDgName(updatedDemande.getDemandeMarchandDgName());
        marchandDTO.setMarchandFormejuridique(updatedDemande.getDemandeMarchandFormejuridique());
        marchandDTO.setMarchandAnneeActivite(updatedDemande.getDemandeMarchandAnneeActivite());
        marchandDTO.setMarchandStatus(Status.JustCreated);

        //email
        this.emailService.sendValidationAcceptedEmail(marchandDTO.getMarchandEmail());

        // Save the new MarchandDTO
        marchandService.saveNewMarchand(marchandDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }


    @PutMapping("/{demandeId}/update/accepted")
    @Transactional
    public ResponseEntity<DemandeDTO> updateDemande(@PathVariable Long demandeId, @RequestBody DemandeDTO demandeDTO) throws MarchandNotFoundException, MessagingException {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeValuesAndAccepted(demandeId,demandeDTO);

        //email
        this.emailService.sendValidationAcceptedEmail(demandeDTO.getDemandeMarchandEmail());

        //
        MarchandDTO marchandDTO = new MarchandDTO();
        marchandDTO.setMarchandEmail(updatedDemande.getDemandeMarchandEmail());
        marchandDTO.setMarchandName(updatedDemande.getDemandeMarchandName());
        marchandDTO.setMarchandDescription(updatedDemande.getDemandeMarchandDescription());
        marchandDTO.setMarchandPhone(updatedDemande.getDemandeMarchandPhone());
        marchandDTO.setMarchandHost(updatedDemande.getDemandeMarchandHost());
        marchandDTO.setMarchandLogoUrl(updatedDemande.getDemandeMarchandLogoUrl());
        marchandDTO.setMarchandTypeActivite(updatedDemande.getDemandeMarchandTypeActivite());
        marchandDTO.setMarchandRcIf(updatedDemande.getDemandeMarchandRcIf());
        marchandDTO.setMarchandSiegeAddresse(updatedDemande.getDemandeMarchandSiegeAddresse());
        marchandDTO.setMarchandDgName(updatedDemande.getDemandeMarchandDgName());
        marchandDTO.setMarchandFormejuridique(updatedDemande.getDemandeMarchandFormejuridique());
        marchandDTO.setMarchandAnneeActivite(updatedDemande.getDemandeMarchandAnneeActivite());
        marchandDTO.setMarchandStatus(Status.JustCreated);

        // Save the new MarchandDTO
        marchandService.saveNewMarchand(marchandDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }


    @PutMapping("/{demandeId}/update")
    public ResponseEntity<DemandeDTO> updateDemandeValues(@PathVariable Long demandeId, @RequestBody DemandeDTO demandeDTO) throws MarchandNotFoundException {
        //
        DemandeDTO updatedDemande = demandeService.UpdateDemandeValues(demandeId,demandeDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }



///****************************************************************************************************
    /// SSE

    @GetMapping("/not-verified-sse")
    public Flux<DemandeDTO> getAllDemandesNotVerifiedSEE() {
        return demandeService.getAllDemandesNotVerifiedSEE();
    }

}
