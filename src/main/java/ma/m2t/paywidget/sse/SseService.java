package ma.m2t.paywidget.sse;
import ma.m2t.paywidget.dto.DemandeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new ArrayList<>();

    public void addSseEmitter(SseEmitter emitter) {
        emitters.add(emitter);
    }

    public void removeSseEmitter(SseEmitter emitter) {
        emitters.remove(emitter);
    }

    public void sendNewDemandEvent(DemandeDTO demandeDTO) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(demandeDTO);
            } catch (IOException e) {
                // Client disconnected, remove the emitter
                deadEmitters.add(emitter);
            }
        }
        emitters.removeAll(deadEmitters);
    }
}
