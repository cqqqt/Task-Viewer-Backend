package com.taskviewer.api.service;

import com.taskviewer.api.model.User;

public interface MailService {

    void send(User user, String subject, String message);

}
