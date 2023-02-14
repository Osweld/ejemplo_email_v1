package com.example.osweldev.controllers;

import com.example.osweldev.dto.MultipleDestination;
import com.example.osweldev.dto.SimpleEmail;
import com.example.osweldev.dto.User;
import com.example.osweldev.services.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/simple")
    ResponseEntity<SimpleEmail> sendSimpleEmail(@RequestBody SimpleEmail simpleEmail){
        emailService.sendSimpleEmail(
                simpleEmail.getTo(),
                simpleEmail.getSubject(),
                simpleEmail.getBody());
        return new ResponseEntity<>(simpleEmail, HttpStatus.OK);
    }

    @PostMapping(value = "/multiple")
    ResponseEntity<MultipleDestination> sendMultipleDestinationEmail(@RequestBody MultipleDestination multipleDestination){
        emailService.sendMultipleDestinationEmail(
                multipleDestination.getTo(),
                multipleDestination.getSubject(),
                multipleDestination.getBody());
        return new ResponseEntity<>(multipleDestination, HttpStatus.OK);
    }

    @PostMapping("files")
    ResponseEntity<SimpleEmail> sendEmailWithAttachment(@RequestPart("simpleEmail") SimpleEmail simpleEmail, @RequestPart("files[]") MultipartFile[] multipartFile) throws MessagingException {
        emailService.sendEmailWithAttachments(simpleEmail,multipartFile);
        return new ResponseEntity<>(simpleEmail,HttpStatus.OK);
    }

    @PostMapping("/mime-template")
    ResponseEntity<SimpleEmail> sendEmailMimeTemplate(@RequestBody SimpleEmail simpleEmail) throws MessagingException {
        emailService.sendEmailWithTemplate(
                simpleEmail.getTo(),
                simpleEmail.getSubject(),
                simpleEmail.getBody());
        return new ResponseEntity<>(simpleEmail, HttpStatus.OK);
    }

    @PostMapping("/freemaker")
    ResponseEntity<User> sendEmailFreeMakerTemplate(@RequestBody User user) throws MessagingException, TemplateException, IOException {
        emailService.sendEmailWithFreeMakerTemplate(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
