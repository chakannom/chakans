package com.chakans.portal.config.thirdparty;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpHostConnectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;

public class DockerTest {

	private DockerClient dockerClient;

	@BeforeEach
	public void setup() {
		dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
	}

	@Test
	public void versionCmdTest() throws HttpHostConnectException {
		dockerClient.versionCmd().exec();
		// if it is generated 'HttpHostConnectException'
		// System.out.println("you must install docker in local. And you need to enable the tcp Socket(tcp://localhost:2375).");
	}

//	@Test
	public void pullImageCmdTest() throws InterruptedException {
		dockerClient.pullImageCmd("minio/minio")
		  .withTag("edge")
		  .exec(new PullImageResultCallback())
		  .awaitCompletion(30, TimeUnit.SECONDS);
	}
	
//	@Test
	public void listContainersCmdAndRemoveContainerCmdTest() {
		List<Container> containers = dockerClient.listContainersCmd()
			.withShowAll(true)
			.withNameFilter(Arrays.asList("dev-minio"))
			.withStatusFilter(Arrays.asList("created", "restarting", "paused", "exited"))
			.exec();
		containers.stream()
			.filter(container -> container.getNames()[0].contains("dev-minio"))
			.forEach(container -> dockerClient.removeContainerCmd(container.getId()).exec());

	}

//	@Test
	public void createContainerCmdAndStartContainerCmdTest() {
		CreateContainerResponse container = dockerClient.createContainerCmd("minio/minio:edge")
			    .withEnv("MINIO_ACCESS_KEY=storage1", "MINIO_SECRET_KEY=storage1", "MINIO_WORM=on")
			    .withHostConfig(HostConfig.newHostConfig().withPortBindings(new PortBinding(Binding.bindPort(9000), ExposedPort.tcp(9000))))
			    .withHostName("dev-minio")
				.withName("dev-minio")
				.withCmd("server", "/data")
				.exec();
		dockerClient.startContainerCmd(container.getId()).exec();
	}
}
