package ca.sfu.cmpt276.grow.with.you.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sfu.cmpt276.grow.with.you.models.ChatMessage;
import ca.sfu.cmpt276.grow.with.you.models.ChatMessageRepository;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository messageRepository;

}
