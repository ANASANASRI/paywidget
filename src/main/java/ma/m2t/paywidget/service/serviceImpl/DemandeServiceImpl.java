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


    @Override
    public DemandeDTO saveNewDemande(DemandeDTO demandeDTO) {
        demandeDTO.setDemandeIsAccepted(false);
        demandeDTO.setDemandeIsVerified(false);
        Demande demande = payMapper.fromDemandeDTO(demandeDTO);
        Demande savedDemande = demandeRepository.save(demande);
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
        return demandes.stream().map(payMapper::fromDemande).collect(Collectors.toList());
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
}
