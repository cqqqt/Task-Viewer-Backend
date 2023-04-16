package com.taskviewer.api.service;

public interface MailService {

  void send(String email, String subject, String message);

}
