package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@RequestMapping("/")
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService =  accountService;
        this.messageService = messageService;
    }

    

    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account){
        Optional<Account> registeredAccount = accountService.registerAccount(account); 
        if(registeredAccount.isPresent()){
            return ResponseEntity.ok(registeredAccount.get());
        }
        else if(accountService.loginAccount(account).isPresent()){
            return ResponseEntity.status(409).build();
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        Optional<Account> loginAccount = accountService.loginAccount(account);
        if(loginAccount.isPresent()){
            return ResponseEntity.ok(loginAccount.get());
        }
        else{
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessageHandler(@RequestBody Message message){
        Optional<Message> createdMessage = messageService.createMessage(message);
        if(createdMessage.isPresent()){
            return ResponseEntity.ok(createdMessage.get());
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler(){
        return ResponseEntity.ok(messageService.retrieveAllMessages());
    }

    @GetMapping("messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        Optional<Message> message = messageService.retrieveMessageById(messageId);
        // System.out.println(message.toString());
        if(message.isPresent()){
            return ResponseEntity.ok(message.get());
        }
        else{
            return ResponseEntity.ok().build();
        }

    }

    @DeleteMapping("messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> deleteMessageByIdHandler(@PathVariable int messageId){
        Integer deletedMessage = messageService.deleteMessageById(messageId);
        if(deletedMessage == 1){
            return ResponseEntity.ok(1);
        }
        else{
            return ResponseEntity.status(200).build();
        }

    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId,@RequestBody Message message){
        boolean updatedMessage = messageService.updateMessageById(messageId, message.getMessageText());
        System.out.println(updatedMessage);
        if(updatedMessage){
            return ResponseEntity.ok(1);
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> retrieveAllMessagesByUser(@PathVariable int accountId){
        List<Message> retrievedMessage = messageService.retrieveMessagesByUser(accountId);
        return ResponseEntity.ok(retrievedMessage);
    }
}

