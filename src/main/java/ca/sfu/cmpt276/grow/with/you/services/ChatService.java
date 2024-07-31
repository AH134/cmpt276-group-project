package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.Chat;
import ca.sfu.cmpt276.grow.with.you.models.ChatRepository;
import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public void createChat(Plant plant, Grower grower, Sponsor sponsor) {
        Chat newChat = new Chat(sponsor, grower, plant);
        chatRepository.save(newChat);
    }

    public Chat getChatById(int chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (chatOptional.isPresent()) {
            return chatOptional.get();
        }

        return null;
    }

    public List<Chat> getChatsByGrower(Grower grower) {
        return chatRepository.findByGrower(grower);
    }

    public List<Chat> getChatsBySponsor(Sponsor sponsor) {
        return chatRepository.findBySponsor(sponsor);
    }
}
