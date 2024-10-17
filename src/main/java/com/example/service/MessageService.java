package com.example.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepositorymp;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepositorymp accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepositorymp accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    

    public Optional<Message> createMessage(Message message){
        if(!message.getMessageText().isBlank() && message.getMessageText().length() <= 255 && accountRepository.existsById(message.getPostedBy())){
            return Optional.of(messageRepository.save(message));
        }
        return Optional.empty() ;
    }

    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
        
    }

    public Optional<Message> retrieveMessageById(int messageId){
        // Message test = messageRepository.getById(messageId);
        // System.out.println(test.toString());
        return messageRepository.findById(messageId);
    }

    public int deleteMessageById(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else{
            return 0;
        }
    }

    public boolean updateMessageById(int messageId, String messageText){
        if( messageText.isBlank() || messageText.isEmpty() || messageText.length() > 255) return false;
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()){
            message.get().setMessageText(messageText);
            messageRepository.save(message.get());
            return true;
        }
        else{
            return false;
        }
    }

    public List<Message> retrieveMessagesByUser(int accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }
}
