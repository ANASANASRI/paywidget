package ma.m2t.paywidget.websocket.controller;

import ma.m2t.paywidget.dto.DemandeDTO;
import ma.m2t.paywidget.model.Demande;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DemandWebSocketController {

    @MessageMapping("/new-demand")
    @SendTo("/topic/demands")
    public DemandeDTO handleNewDemand(DemandeDTO demandeDTO) {
        // Process the new demand here if needed
        return demandeDTO;
    }
}