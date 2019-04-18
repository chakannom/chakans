package com.chakans.account.web.rest.anonymous;

import com.chakans.core.config.constants.Constants;
import com.chakans.core.service.noop.NoOpMailSenderService;

import com.chakans.account.domain.User;
import com.chakans.account.repository.UserRepository;
import com.chakans.core.security.SecurityUtils;
import com.chakans.core.service.MailSenderService;
import com.chakans.account.service.UserService;
import com.chakans.account.service.dto.UserDTO;
import com.chakans.core.web.rest.errors.*;
import com.chakans.account.web.rest.anonymous.model.request.KeyAndPasswordRequestModel;
import com.chakans.account.web.rest.anonymous.model.request.PasswordChangeRequestModel;
import com.chakans.account.web.rest.anonymous.model.request.UserRequestModel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController("account-anonymous-account-resource")
@RequestMapping(value = "/apis", produces = Constants.APPLICATION_VND_ACCOUNT_ANONYMOUS_JSON)
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private MailSenderService<User> mailSenderService = new NoOpMailSenderService<User>();

    public AccountResource(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void setMailSenderService(MailSenderService<User> mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param userRequestModel the user Request Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody UserRequestModel userRequestModel) {
        if (!checkPasswordLength(userRequestModel.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(userRequestModel, userRequestModel.getPassword());
        mailSenderService.sendActivationEmail(user);
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthoritiesAndAgreements()
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl(), userDTO.getAgreements().get("promotional_emails"));
    }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeRequestModel current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeRequestModel passwordChangeRequestModel) {
        if (!checkPasswordLength(passwordChangeRequestModel.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeRequestModel.getCurrentPassword(), passwordChangeRequestModel.getNewPassword());
    }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        mailSenderService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPasswordRequestModel the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordRequestModel keyAndPasswordRequestModel) {
        if (!checkPasswordLength(keyAndPasswordRequestModel.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPasswordRequestModel.getNewPassword(), keyAndPasswordRequestModel.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= UserRequestModel.PASSWORD_MIN_LENGTH &&
            password.length() <= UserRequestModel.PASSWORD_MAX_LENGTH;
    }
}
