package com.chakans.drive.web.rest.user;

import com.chakans.core.config.constants.AuthoritiesConstants;
import com.chakans.core.config.constants.Constants;
import com.chakans.core.security.SecurityUtils;
import com.chakans.drive.domain.DrivePersonalNode;
import com.chakans.drive.repository.DrivePersonalNodeRepository;
import com.chakans.drive.service.DrivePersonalFileService;
import com.chakans.drive.service.DriveStorageService;
import com.chakans.drive.service.dto.DrivePersonalFileDTO;
import com.chakans.drive.web.rest.errors.ParentNodeNotFoundException;
import com.chakans.drive.web.rest.user.model.request.FileCreateRequestModel;
import com.chakans.drive.web.rest.user.model.response.FileResponseModel;
import com.google.common.collect.ImmutableMap;

import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController("drive-v1-user-drive-resource")
@RequestMapping(value = "/apis/drive/v1", produces = Constants.APPLICATION_VND_DRIVE_V1_USER_JSON)
@PreAuthorize("hasRole(\"" + AuthoritiesConstants.USER + "\")")
public class DriveResource {

    private static class DriveResourceException extends RuntimeException {
        private DriveResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(DriveResource.class);

    private final DrivePersonalNodeRepository drivePersonalNodeRepository;

    private final DrivePersonalFileService drivePersonalFileService;

    private final DriveStorageService driveStorageService;

    public DriveResource(DrivePersonalNodeRepository drivePersonalNodeRepository, DrivePersonalFileService drivePersonalFileService, DriveStorageService driveStorageService) {
        this.drivePersonalNodeRepository = drivePersonalNodeRepository;
        this.drivePersonalFileService = drivePersonalFileService;
        this.driveStorageService = driveStorageService;
    }

    // mimetype 활용하기 folder, file(...) 등
    @PostMapping("/files")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseModel createFile(@Valid @RequestBody FileCreateRequestModel fileCreateRequestModel) {
        log.debug("REST request to create My File : {}", fileCreateRequestModel);
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new DriveResourceException("Current user login not found"));
        Optional<DrivePersonalNode> existingNode = drivePersonalNodeRepository.findOneByIdAndUserLogin(fileCreateRequestModel.getParent().getId(), userLogin);
        if (!existingNode.isPresent()) {
            throw new ParentNodeNotFoundException();
        }
        DrivePersonalFileDTO newDrivePersonalFileDTO = drivePersonalFileService.createMyDrivePersonalFile(fileCreateRequestModel.getName(), fileCreateRequestModel.getMimeType(), fileCreateRequestModel.getSize(), fileCreateRequestModel.getParent().getId()).get();
        return new FileResponseModel(newDrivePersonalFileDTO);
    }

    @GetMapping("/files")
    public List<Object> getFiles() {
        return null;
    }

    @GetMapping("/files/presigned-put-url")
    public ResponseEntity<Map<String, String>> getFilePresignedPutUrl(@RequestParam("filename") String filename) {
        return ResponseUtil.wrapOrNotFound(driveStorageService.getPresignedPutUrl(filename).map(url -> ImmutableMap.of("url", url)));
    }
}
