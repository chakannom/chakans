package com.chakans.blog.web.rest.user;

import com.chakans.core.config.constants.Constants;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.chakans.blog.service.BlogUserProfileService;
import com.chakans.blog.web.rest.user.model.request.BlogUserProfileRequestModel;
import com.chakans.blog.web.rest.user.model.response.BlogUserProfileResponseModel;
import com.chakans.core.config.constants.AuthoritiesConstants;

import javax.validation.Valid;

@RestController("blog-v1-user-blog-user-profile-resource")
@RequestMapping(value = "/apis/blog/v1", produces = Constants.APPLICATION_VND_BLOG_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class BlogUserProfileResource {

	private final Logger log = LoggerFactory.getLogger(BlogUserProfileResource.class);

	private final BlogUserProfileService blogUserProfileService;

	public BlogUserProfileResource(BlogUserProfileService blogUserProfileService) {
		this.blogUserProfileService = blogUserProfileService;
	}

	@PostMapping("/users/my")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMyProfile(@Valid @RequestBody BlogUserProfileRequestModel bupRequestModel) {
	    log.debug("REST request to save My UserProfile : {}", bupRequestModel);
	    blogUserProfileService.createMyProfile(bupRequestModel.getNickname());
	}

    @PatchMapping("/users/my")
    public void patchMyProfile(
            @RequestHeader("fields") String fields,
            @Valid @RequestBody BlogUserProfileRequestModel bupRequestModel) {
        log.debug("REST request to patch up My UserProfile : {}", bupRequestModel);
        blogUserProfileService.patchMyProfile(bupRequestModel.getNickname(), bupRequestModel.getEmail(),
                bupRequestModel.getImageUrl(), bupRequestModel.isOpenedProfile(), bupRequestModel.isOpenedEmail(), fields.split(","));
    }

    @GetMapping("/users/my")
    public ResponseEntity<BlogUserProfileResponseModel> getMyProfile() {
        log.debug("REST request to get My UserProfile");
        return ResponseUtil.wrapOrNotFound(blogUserProfileService.getMyProfile().map(BlogUserProfileResponseModel::new));
    }
}
