package flab.gumipayments.infrastructure.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface MessageCallBack {

    void setMimeMessage(MimeMessage message) throws MessagingException;
}
