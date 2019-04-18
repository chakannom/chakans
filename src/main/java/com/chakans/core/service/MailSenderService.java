package com.chakans.core.service;

/**
 * Interface for sending emails.
 */
public interface MailSenderService<A> {

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    public void sendEmailFromTemplate(A user, String templateName, String titleKey);

    public void sendActivationEmail(A user);

    public void sendCreationEmail(A user);

    public void sendPasswordResetMail(A user);
}
