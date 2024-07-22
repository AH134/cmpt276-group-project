package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import ca.sfu.cmpt276.grow.with.you.models.Chat;
import ca.sfu.cmpt276.grow.with.you.models.ChatMessage;
import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.models.User;
import ca.sfu.cmpt276.grow.with.you.services.ChatMessageService;
import ca.sfu.cmpt276.grow.with.you.services.ChatService;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.setHttpHeader;
import ca.sfu.cmpt276.utils.chat.ChatNotification;
import ca.sfu.cmpt276.utils.enums.UserRole;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

        ChatNotification chatNotification = new ChatNotification(chatMessage.getMessageId(), chatMessage.getSenderId(),
                chatMessage.getRecipientId(), chatMessage.getContent());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getRecipientId()), "/queue/messsages",
                chatNotification);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/messages")
    public String getMessages(Model model, HttpSession session, HttpServletResponse response) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        if (user == null) {
            return "redirect:/";
        }

        List<Chat> chatList;
        if (user.getRole() == UserRole.GROWER) {
            chatList = chatService.getChatsByGrower((Grower) user);
            model.addAttribute("chats", chatList);
            model.addAttribute("selectedChat", chatList.get(0));
        } else if (user.getRole() == UserRole.SPONSOR) {
            chatList = chatService.getChatsBySponsor((Sponsor) user);
            model.addAttribute("chats", chatList);
            model.addAttribute("selectedChat", chatList.get(0));
        }

        return "protected/user/messages";
    }

    @GetMapping("/messages/{chatId}")
    public String getPlantMessages(@PathVariable("chatId") int chatId, Model model, HttpSession session,
            HttpServletResponse response) {
        User user = userService.getUserBySession(session);
        setHttpHeader.setHeader(response);
        if (user == null) {
            return "redirect:/";
        }

        Chat chat = chatService.getChatById(chatId);
        // if (chat == null) {
        // return "redirect/messages";
        // }
        model.addAttribute("messages", chat.getMessages());
        System.out.println(chat.getMessages());

        return "protected/user/plantChat";
    }

}
