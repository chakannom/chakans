package com.chakans.core.service.noop;

import com.chakans.core.service.MailSenderService;

/**
 * Service for disabling sending emails.
 * @param <T>
 */
public class NoOpMailSenderService<A> implements MailSenderService<A> {

    @Override
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        // No operation
    }

    @Override
    public void sendEmailFromTemplate(A user, String templateName, String titleKey) {
        // No operation
    }

    @Override
    public void sendActivationEmail(A user) {
        // No operation
    }

    @Override
    public void sendCreationEmail(A user) {
        // No operation
    }

    @Override
    public void sendPasswordResetMail(A user) {
        // No operation
    }
}
