package com.taskviewer.api.service;

import com.taskviewer.api.model.User;

public interface MailService {

    void send(final User user, final String subject, final String message);

}
