package ma.m2t.paywidget.controller;

import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.dto.MarchandDTO;
import ma.m2t.paywidget.enums.Status;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.service.DemandeService;
import ma.m2t.paywidget.service.MarchandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("demandes")
public class DemandeApi {
    private final DemandeService demandeService;
    private MarchandService marchandService;

    @PostMapping
    public ResponseEntity<DemandeDTO> saveNewDemande(@RequestBody DemandeDTO demandeDTO) {
        DemandeDTO savedDemande = demandeService.saveNewDemande(demandeDTO);
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
    public ResponseEntity<DemandeDTO> updateDemandeRejected(@PathVariable Long demandeId) throws MarchandNotFoundException {
        DemandeDTO updatedDemande = demandeService.UpdateDemandeRejected(demandeId);
        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }

    @PutMapping("/{demandeId}/accepted")
    public ResponseEntity<DemandeDTO> updateDemandeAccepted(@PathVariable Long demandeId) throws MarchandNotFoundException {
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

        // Save the new MarchandDTO
        marchandService.saveNewMarchand(marchandDTO);

        return new ResponseEntity<>(updatedDemande, HttpStatus.OK);
    }
}
