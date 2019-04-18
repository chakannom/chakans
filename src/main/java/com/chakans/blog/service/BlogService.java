package com.chakans.blog.service;

import java.util.Arrays;
import java.util.Optional;

import com.chakans.account.repository.UserRepository;
import com.chakans.blog.config.constants.BlogAuthoritiesConstants;
import com.chakans.blog.config.constants.BlogEnumsConstants;
import com.chakans.blog.domain.Blog;
import com.chakans.blog.domain.BlogDesign;
import com.chakans.blog.domain.BlogUser;
import com.chakans.blog.repository.*;
import com.chakans.blog.service.dto.BlogDTO;
import com.chakans.core.security.SecurityUtils;
import com.chakans.portal.config.ApplicationProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing blogs.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;
    
    private final BlogDesignRepository blogDesignRepository;
    
    private final BlogUserRepository blogUserRepository;

    private final BlogThemeTemplateRepository blogThemeTemplateRepository;

    private final UserRepository userRepository;

    private final BlogAuthorityRepository blogAuthorityRepository;

    private final ApplicationProperties applicationProperties;

    public BlogService(BlogRepository blogRepository, BlogDesignRepository blogDesignRepository, BlogUserRepository blogUserRepository, BlogThemeTemplateRepository blogThemeTemplateRepository, UserRepository userRepository, BlogAuthorityRepository blogAuthorityRepository, ApplicationProperties applicationProperties) {
        this.blogRepository = blogRepository;
        this.blogDesignRepository = blogDesignRepository;
        this.blogUserRepository = blogUserRepository;
        this.blogThemeTemplateRepository = blogThemeTemplateRepository;
        this.userRepository = userRepository;
        this.blogAuthorityRepository = blogAuthorityRepository;
        this.applicationProperties = applicationProperties;
    }

    public Optional<BlogDTO> createMyBlog(String title, String subdomain, Long themeId) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .map(user -> {
                Blog newBlog = new Blog();
                newBlog.setTitle(title);
                newBlog.setUrl(subdomain  + "." + applicationProperties.getBlog().getDomain());
                newBlog.setLangKey(user.getLangKey());
                newBlog.setStatus(BlogEnumsConstants.BLOG_SATAUS.OPENED);
                blogRepository.save(newBlog);
                log.debug("Created Information for My Blog: {}", newBlog);
                
                BlogDesign newBlogDesign = new BlogDesign();
                newBlogDesign.setBlogId(newBlog.getId());
                newBlogDesign.setTheme(blogThemeTemplateRepository.findById(themeId).get().getContent());
                blogDesignRepository.save(newBlogDesign);
                log.debug("Created Information for My BlogDesign: {}", newBlogDesign);
                
                BlogUser newBlogUser = new BlogUser();
                newBlogUser.setBlogId(newBlog.getId());
                newBlogUser.setUserLogin(user.getLogin());
                newBlogUser.setBlogAuthority(blogAuthorityRepository.findById(BlogAuthoritiesConstants.ADMIN).get());
                blogUserRepository.save(newBlogUser);
                log.debug("Created Information for My BlogUser: {}", newBlogUser);
                
                return new BlogDTO(newBlog, newBlogDesign);
            });
    }

    public Optional<BlogDTO> updateMyBlog(Long blogId, String title, String description, String url, String customUrl, String langKey, BlogEnumsConstants.BLOG_SATAUS status, Integer designWidth, Integer designLeftbarWidth, Integer designRightbarWidth, String theme, Boolean topBar) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                Blog blog = blogRepository.findById(blogId).get();
                blog.setTitle(title);
                blog.setDescription(description);
                blog.setUrl(url);
                blog.setCustomUrl(customUrl);
                blog.setLangKey(langKey);
                blog.setStatus(status);
                log.debug("Changed Information for My Blog: {}", blog);
                
                BlogDesign blogDesign = blogDesignRepository.findById(blogId).get();
                blogDesign.setWidth(designWidth);
                blogDesign.setLeftbarWidth(designLeftbarWidth);
                blogDesign.setRightbarWidth(designRightbarWidth);
                blogDesign.setTheme(theme);
                blogDesign.setTopBar(topBar);
                log.debug("Changed Information for My BlogDesign: {}", blogDesign);
                
                return new BlogDTO(blog, blogDesign);
            });
    }

    public Optional<BlogDTO> patchMyBlog(Long blogId, String title, String description, String url, String customUrl, String langKey, BlogEnumsConstants.BLOG_SATAUS status, Integer designWidth, Integer designLeftbarWidth, Integer designRightbarWidth, String theme, Boolean topBar, String[] fields) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                Blog blog = blogRepository.findById(blogId).get();
                if (Arrays.stream(fields).anyMatch("title"::equalsIgnoreCase)) {
                    blog.setTitle(title);
                }
                if (Arrays.stream(fields).anyMatch("description"::equalsIgnoreCase)) {
                    blog.setDescription(description);
                }
                if (Arrays.stream(fields).anyMatch("url"::equalsIgnoreCase)) {
                    blog.setUrl(url);
                }
                if (Arrays.stream(fields).anyMatch("customUrl"::equalsIgnoreCase)) {
                    blog.setCustomUrl(customUrl);
                }
                if (Arrays.stream(fields).anyMatch("langKey"::equalsIgnoreCase)) {
                    blog.setLangKey(langKey);
                }
                if (Arrays.stream(fields).anyMatch("status"::equalsIgnoreCase)) {
                    blog.setStatus(status);
                }
                
                BlogDesign blogDesign = blogDesignRepository.findById(blogId).get();
                if (Arrays.stream(fields).anyMatch("design.width"::equalsIgnoreCase)) {
                    blogDesign.setWidth(designWidth);
                }
                if (Arrays.stream(fields).anyMatch("design.leftbarWidth"::equalsIgnoreCase)) {
                    blogDesign.setLeftbarWidth(designLeftbarWidth);
                }
                if (Arrays.stream(fields).anyMatch("design.rightbarWidth"::equalsIgnoreCase)) {
                    blogDesign.setRightbarWidth(designRightbarWidth);
                }
                if (Arrays.stream(fields).anyMatch("design.theme"::equalsIgnoreCase)) {
                    blogDesign.setTheme(theme);
                }
                if (Arrays.stream(fields).anyMatch("design.topBar"::equalsIgnoreCase)) {
                    blogDesign.setTopBar(topBar);
                }

                return new BlogDTO(blog, blogDesign);
            });
    }

    @Transactional(readOnly = true)
    public Page<BlogDTO> getMyBlogs(Pageable pageable) {
        return SecurityUtils.getCurrentUserLogin()
            .map(userLogin -> blogUserRepository.findAllByUserLogin(pageable, userLogin)).get()
            .map(blogUser -> {
                Blog blog = blogRepository.findById(blogUser.getBlogId()).get();
                BlogDesign blogDesign = blogDesignRepository.findById(blogUser.getBlogId()).get();
                return new BlogDTO(blog, blogDesign);
            });
    }

    @Transactional(readOnly = true)
    public Optional<BlogDTO> getMyBlogById(Long blogId) {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userLogin -> blogUserRepository.findOneByBlogIdAndUserLogin(blogId, userLogin))
            .map(blogUser -> {
                Blog blog = blogRepository.findById(blogId).get();
                BlogDesign blogDesign = blogDesignRepository.findById(blogId).get();
                return new BlogDTO(blog, blogDesign);
            });
    }

    @Transactional(readOnly = true)
    public Optional<BlogDTO> getBlogBySubdomain(String subdomain) {
        String url = subdomain + "." + applicationProperties.getBlog().getDomain();
        return blogRepository.findOneByUrl(url).map(blog -> new BlogDTO(blog, null));
    }

    @Transactional(readOnly = true)
    public Optional<BlogDTO> getBlogByUrl(String url) {
        return blogRepository.findOneByUrl(url)
            .map(blog -> {
                BlogDesign blogDesign = blogDesignRepository.findById(blog.getId()).get();
                return new BlogDTO(blog, blogDesign);
            });
    }




    @Transactional(readOnly = true)
    public Optional<BlogDTO> getBlogById(Long blogId) {
        return blogRepository.findById(blogId)
            .map(blog -> {
                BlogDesign blogDesign = blogDesignRepository.findById(blog.getId()).get();
                return new BlogDTO(blog, blogDesign);
            });
    }

}
