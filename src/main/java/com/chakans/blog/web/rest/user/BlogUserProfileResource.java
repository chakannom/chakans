package com.chakans.blog.web.rest.user;

import com.chakans.core.config.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.chakans.blog.service.BlogUserProfileService;
import com.chakans.blog.service.dto.BlogUserProfileDTO;
import com.chakans.blog.web.rest.user.model.request.BlogUserProfileRequestModel;
import com.chakans.blog.web.rest.user.model.response.BlogUserProfileResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;

import javax.validation.Valid;
import java.util.Optional;

@RestController("blog-v1-user-blog-user-profile-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogUserProfileResource {

	private final Logger log = LoggerFactory.getLogger(BlogUserProfileResource.class);

	private final BlogUserProfileService blogUserProfileService;

	public BlogUserProfileResource(BlogUserProfileService blogUserProfileService) {
		this.blogUserProfileService = blogUserProfileService;
	}

    @PutMapping("/users/my")
    @ResponseStatus(HttpStatus.OK)
	public BlogUserProfileResponseModel updateMyProfile(@Valid @RequestBody BlogUserProfileRequestModel bupRequestModel) {
        log.debug("REST request to update My UserProfile : {}", bupRequestModel);
        if (!blogUserProfileService.getMyProfile().isPresent()) {
            blogUserProfileService.createMyProfile();
        }
        BlogUserProfileDTO updatedUserProfileDTO = blogUserProfileService.updateMyProfile(bupRequestModel.getNickname(), bupRequestModel.getEmail(), bupRequestModel.getImageUrl(), bupRequestModel.isOpenedProfile(), bupRequestModel.isOpenedEmail()).get();
        return new BlogUserProfileResponseModel(updatedUserProfileDTO);
    }

    @PatchMapping("/users/my")
    @ResponseStatus(HttpStatus.OK)
    public BlogUserProfileResponseModel patchMyProfile(
            @RequestHeader("fields") String fields,
            @Valid @RequestBody BlogUserProfileRequestModel bupRequestModel) {

        log.debug("REST request to patch up My UserProfile : {}", bupRequestModel);
        BlogUserProfileDTO patchedUserProfileDTO = blogUserProfileService.patchMyProfile(bupRequestModel.getNickname(), bupRequestModel.getEmail(), bupRequestModel.getImageUrl(), bupRequestModel.isOpenedProfile(), bupRequestModel.isOpenedEmail(), fields.split(",")).get();
        return new BlogUserProfileResponseModel(patchedUserProfileDTO);
    }

    @GetMapping("/users/my")
    @ResponseStatus(HttpStatus.OK)
    public BlogUserProfileResponseModel getMyProfile() {
        log.debug("REST request to get My UserProfile");
        Optional<BlogUserProfileDTO> existingMyProfile =  blogUserProfileService.getMyProfile();
        if (!existingMyProfile.isPresent()) {
            existingMyProfile = blogUserProfileService.createMyProfile();
        }
        return existingMyProfile.map(BlogUserProfileResponseModel::new).get();
    }
}
