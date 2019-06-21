package com.chakans.core.config.thirdparty.docker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.dockerjava.api.model.Image;

class DockerHelperIT {

	private final String serverUrl = "tcp://localhost:2375";

	private final String imageName = "hello-world";

	private final String tag = "latest";

	private DockerHelper dockerHelper;

	@BeforeEach
	void setup() throws Exception {
		this.dockerHelper = new DockerHelper(serverUrl, imageName, tag);
	}
	
	@Test
	void testGetListImagesBeforePullImage() {
		List<Image> images = dockerHelper.getListImages();

		assertThat(images).isEmpty();
		assertThat(images).size().isEqualTo(0);
	}

	@Test
	void testGetListImagesAfterPullImage() {
		boolean isPullImage = dockerHelper.pullImage();

		assertThat(isPullImage).isTrue();

		List<Image> images = dockerHelper.getListImages();

		assertThat(images).isNotEmpty();
		assertThat(images).size().isEqualTo(1);
        assertThat(images.get(0).getRepoTags()).isNotEmpty();
		assertThat(images.get(0).getRepoTags()[0]).isEqualTo("hello-world:latest");

		boolean isRemoveImage = dockerHelper.removeImage(true);

		assertThat(isRemoveImage).isTrue();
	}
}
