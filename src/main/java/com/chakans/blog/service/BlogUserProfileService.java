package com.chakans.blog.service;

import java.util.Arrays;
import java.util.Optional;

import com.chakans.account.repository.UserRepository;
import com.chakans.blog.domain.BlogUserProfile;
import com.chakans.blog.repository.BlogUserProfileRepository;
import com.chakans.blog.service.dto.BlogUserProfileDTO;
import com.chakans.core.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing userProfiles.
 */
@Service
@Transactional
public class BlogUserProfileService {

	private final Logger log = LoggerFactory.getLogger(BlogUserProfileService.class);

	private final BlogUserProfileRepository blogUserProfileRepository;

	private final UserRepository userRepository;

	public BlogUserProfileService(BlogUserProfileRepository blogUserProfileRepository, UserRepository userRepository) {
		this.blogUserProfileRepository = blogUserProfileRepository;
		this.userRepository = userRepository;
	}

    public Optional<BlogUserProfileDTO> createMyProfile() {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .map(user -> {
                BlogUserProfile blogUserProfile = new BlogUserProfile();
                blogUserProfile.setUserLogin(user.getLogin());
                blogUserProfile.setNickname(null);
                blogUserProfile.setEmail(user.getEmail());
                blogUserProfile.setOpenedProfile(false);
                blogUserProfile.setOpenedEmail(false);
                blogUserProfileRepository.save(blogUserProfile);
                log.debug("Created Information for BlogUserProfile: {}", blogUserProfile);
                return new BlogUserProfileDTO(blogUserProfile);
            });
    }

    public Optional<BlogUserProfileDTO> updateMyProfile(String nickname, String email, String imageUrl, Boolean openedProfile, Boolean openedEmail) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(blogUserProfileRepository::findOneByUserLogin)
            .map(blogUserProfile -> {
                blogUserProfile.setNickname(nickname);
                blogUserProfile.setEmail(email);
                blogUserProfile.setImageUrl(imageUrl);
                blogUserProfile.setOpenedProfile(openedProfile);
                blogUserProfile.setOpenedEmail(openedEmail);
                log.debug("Changed Information for UserProfile: {}", blogUserProfile);
                return new BlogUserProfileDTO(blogUserProfile);
            });
    }

    public Optional<BlogUserProfileDTO> patchMyProfile(String nickname, String email, String imageUrl, Boolean openedProfile, Boolean openedEmail, String[] fields) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(blogUserProfileRepository::findOneByUserLogin)
            .map(blogUserProfile -> {
                if (Arrays.stream(fields).anyMatch("nickname"::equalsIgnoreCase)) {
                    blogUserProfile.setNickname(nickname);
                }
                if (Arrays.stream(fields).anyMatch("email"::equalsIgnoreCase)) {
                    blogUserProfile.setEmail(email);
                }
                if (Arrays.stream(fields).anyMatch("imageUrl"::equalsIgnoreCase)) {
                    blogUserProfile.setImageUrl(imageUrl);
                }
                if (Arrays.stream(fields).anyMatch("openedProfile"::equalsIgnoreCase)) {
                    blogUserProfile.setOpenedProfile(openedProfile);
                }
                if (Arrays.stream(fields).anyMatch("openedEmail"::equalsIgnoreCase)) {
                    blogUserProfile.setOpenedEmail(openedEmail);
                }
                log.debug("Changed Information for UserProfile: {}", blogUserProfile);
                return new BlogUserProfileDTO(blogUserProfile);                
            });
    }

    @Transactional(readOnly = true)
    public Optional<BlogUserProfileDTO> getMyProfile() {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(blogUserProfileRepository::findOneByUserLogin)
            .map(BlogUserProfileDTO::new);
    }
}
