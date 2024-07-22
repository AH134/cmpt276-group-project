package ca.sfu.cmpt276.grow.with.you.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.ChatMessage;
import ca.sfu.cmpt276.grow.with.you.models.ChatMessageRepository;
import ca.sfu.cmpt276.utils.chat.ChatNotification;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository messageRepository;

    public ChatNotification save(ChatMessage chatMessage) {
        ChatMessage savedChatMessage = messageRepository.save(chatMessage);
        ChatNotification chatNotification = new ChatNotification(savedChatMessage.getMessageId(),
                savedChatMessage.getChat().getChatId(), savedChatMessage.getSenderId(),
                savedChatMessage.getRecipientId(), savedChatMessage.getContent());

        return chatNotification;
    }

}
