package com.example.domain.port.output.publisher;

import com.example.core.valueobject.Email;
import com.example.core.valueobject.EmailCheck;

public interface EmailPublisher {
    void sendMail(Email email);

    boolean CheckEmail(EmailCheck checkMail);
}
