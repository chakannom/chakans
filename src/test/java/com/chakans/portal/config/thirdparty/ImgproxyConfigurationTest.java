package com.chakans.portal.config.thirdparty;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

class ImgproxyConfigurationTest {

	private DockerClient dockerClient;

	@BeforeEach
	public void setup() {
		dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
