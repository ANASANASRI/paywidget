package ma.m2t.paywidget.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.exceptions.MarchandNotFoundException;
import ma.m2t.paywidget.mappers.PayMapperImpl;
import ma.m2t.paywidget.model.Demande;
import ma.m2t.paywidget.repository.DemandeRepository;
import ma.m2t.paywidget.service.DemandeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@CrossOrigin("*")
public class DemandeServiceImpl implements DemandeService {
    private final DemandeRepository demandeRepository;
    private final PayMapperImpl payMapper;
//    private SimpMessagingTemplate messagingTemplate;


    @Override
    public DemandeDTO saveNewDemande(DemandeDTO demandeDTO) {
        demandeDTO.setDemandeIsAccepted(false);
        demandeDTO.setDemandeIsVerified(false);
        Demande demande = payMapper.fromDemandeDTO(demandeDTO);
        Demande savedDemande = demandeRepository.save(demande);
//        // After saving the demand, send it to WebSocket clients
//        messagingTemplate.convertAndSend("/topic/newDemande", demandeDTO);
        return payMapper.fromDemande(savedDemande);
    }

    @Override
    public List<DemandeDTO> getAllDemandes() {
        List<Demande> demandes = demandeRepository.findAll();
        return demandes.stream().map(payMapper::fromDemande).collect(Collectors.toList());
    }

    @Override
    public DemandeDTO getDemande(Long demandeId) {
        Optional<Demande> demandeOptional = demandeRepository.findById(demandeId);
        Demande demande = demandeOptional.orElseThrow(() -> new EntityNotFoundException("Demande not found with id: " + demandeId));
        return payMapper.fromDemande(demande);
    }

    @Override
    public List<DemandeDTO> getAllDemandesNotVerified() {
        List<Demande> demandes = demandeRepository.findAllByDemandeIsVerifiedFalse();
        return demandes.stream()
                .sorted(Comparator.comparing(Demande::getDemandeId))
                .map(payMapper::fromDemande)
                .collect(Collectors.toList());
    }


    @Override
    public DemandeDTO UpdateDemandeRejected(Long demandeId) throws MarchandNotFoundException {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new MarchandNotFoundException("Demande not found with ID: " + demandeId));
        demande.setDemandeIsVerified(true);
        demande.setDemandeIsAccepted(false);
        Demande savedDemande = demandeRepository.save(demande);
        return payMapper.fromDemande(savedDemande);
    }

    @Override
    public DemandeDTO UpdateDemandeAccepted(Long demandeId) throws MarchandNotFoundException {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new MarchandNotFoundException("Demande not found with ID: " + demandeId));
        demande.setDemandeIsVerified(true);
        demande.setDemandeIsAccepted(true);
        Demande savedDemande = demandeRepository.save(demande);
        // Implement logic to save merchand here
        return payMapper.fromDemande(savedDemande);
    }

    @Override
    public DemandeDTO UpdateDemandeValuesAndAccepted(Long demandeId, DemandeDTO demandeDTO) throws MarchandNotFoundException {
        Demande demande = demandeRepository.findById(demandeId)
            .orElseThrow(() -> new MarchandNotFoundException("Demande not found with ID: " + demandeId));

        // Update all attributes from demandeDTO to demande
        demande.setDemandeMarchandName(demandeDTO.getDemandeMarchandName());
        demande.setDemandeMarchandDescription(demandeDTO.getDemandeMarchandDescription());
        demande.setDemandeMarchandPhone(demandeDTO.getDemandeMarchandPhone());
        demande.setDemandeMarchandHost(demandeDTO.getDemandeMarchandHost());
        demande.setDemandeMarchandEmail(demandeDTO.getDemandeMarchandEmail());
        demande.setDemandeMarchandLogoUrl(demandeDTO.getDemandeMarchandLogoUrl());
        demande.setDemandeMarchandStatus(demandeDTO.getDemandeMarchandStatus());
        demande.setDemandeMarchandTypeActivite(demandeDTO.getDemandeMarchandTypeActivite());
        demande.setDemandeMarchandRcIf(demandeDTO.getDemandeMarchandRcIf());
        demande.setDemandeMarchandSiegeAddresse(demandeDTO.getDemandeMarchandSiegeAddresse());
        demande.setDemandeMarchandDgName(demandeDTO.getDemandeMarchandDgName());
        demande.setDemandeMarchandFormejuridique(demandeDTO.getDemandeMarchandFormejuridique());
        demande.setDemandeMarchandAnneeActivite(demandeDTO.getDemandeMarchandAnneeActivite());

        // Set validation fields
        demande.setDemandeIsVerified(true);
        demande.setDemandeIsAccepted(true);

        Demande savedDemande = demandeRepository.save(demande);

        // Implement logic to save related entities (e.g., marchand) here if needed
        return payMapper.fromDemande(savedDemande);
    }




    @Override
    public DemandeDTO UpdateDemandeValues(Long demandeId, DemandeDTO demandeDTO) throws MarchandNotFoundException {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new MarchandNotFoundException("Demande not found with ID: " + demandeId));

        // Update all attributes from demandeDTO to demande
        demande.setDemandeMarchandName(demandeDTO.getDemandeMarchandName());
        demande.setDemandeMarchandDescription(demandeDTO.getDemandeMarchandDescription());
        demande.setDemandeMarchandPhone(demandeDTO.getDemandeMarchandPhone());
        demande.setDemandeMarchandHost(demandeDTO.getDemandeMarchandHost());
        demande.setDemandeMarchandEmail(demandeDTO.getDemandeMarchandEmail());
        demande.setDemandeMarchandLogoUrl(demandeDTO.getDemandeMarchandLogoUrl());
        demande.setDemandeMarchandStatus(demandeDTO.getDemandeMarchandStatus());
        demande.setDemandeMarchandTypeActivite(demandeDTO.getDemandeMarchandTypeActivite());
        demande.setDemandeMarchandRcIf(demandeDTO.getDemandeMarchandRcIf());
        demande.setDemandeMarchandSiegeAddresse(demandeDTO.getDemandeMarchandSiegeAddresse());
        demande.setDemandeMarchandDgName(demandeDTO.getDemandeMarchandDgName());
        demande.setDemandeMarchandFormejuridique(demandeDTO.getDemandeMarchandFormejuridique());
        demande.setDemandeMarchandAnneeActivite(demandeDTO.getDemandeMarchandAnneeActivite());

        // Set validation fields
        demande.setDemandeIsVerified(false);
        demande.setDemandeIsAccepted(false);

        Demande savedDemande = demandeRepository.save(demande);

        // Implement logic to save related entities (e.g., marchand) here if needed
        return payMapper.fromDemande(savedDemande);
    }
///****************************************************************************************************
    /// SEE

    @Override
    public Flux<DemandeDTO> getAllDemandesNotVerifiedSEE() {
        return Flux.defer(() ->
                Flux.fromIterable(demandeRepository.findAllByDemandeIsVerifiedFalse())
                        .map(payMapper::fromDemande)
        );
    }

}
