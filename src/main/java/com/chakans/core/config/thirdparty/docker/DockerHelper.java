package com.chakans.core.config.thirdparty.docker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;

public class DockerHelper {

	private final Logger log = LoggerFactory.getLogger(DockerHelper.class);

	private final DockerClient dockerClient;

	private final String imageName;

	private final String tag;

	public DockerHelper(String imageName, String tag) throws DockerClientException {
		this("tcp://localhost:2375", imageName, tag);
	}

	public DockerHelper(String serverUrl, String imageName, String tag) throws DockerClientException {
		this.dockerClient = DockerClientBuilder.getInstance(serverUrl).withDockerCmdExecFactory(new NettyDockerCmdExecFactory()).build();
		try {
			dockerClient.versionCmd().exec();
		} catch (Exception e) {
			throw new DockerClientException("It failed to connect to docker host.", e);
		}
		this.imageName = imageName;
		this.tag = tag;
	}

	public List<Image> getListImages() {
		return dockerClient.listImagesCmd()
				.withShowAll(true)
				.withImageNameFilter(imageName + ":" + tag)
				.exec();
	}

	public boolean isExistedImage() {
		return !getListImages().isEmpty();
	}

	public boolean pullImage() throws DockerClientException {
		try {
			boolean isPullImage = dockerClient.pullImageCmd(imageName)
					.withTag(tag)
					.exec(new PullImageResultCallback())
					.awaitCompletion(120, TimeUnit.SECONDS);
			log.debug("pulled image (" + imageName + ":" + tag + ")");
			return isPullImage;
		} catch (InterruptedException e) {
			throw new DockerClientException("It failed to pull image (" + imageName + ":" + tag + ") .", e);
		}
	}

	public List<Container> getListContainers(String name) {
		return dockerClient.listContainersCmd()
				.withShowAll(true)
				.withNameFilter(Arrays.asList(name))
				.exec();
	}

	public boolean isExistedRunningContainer(String name) {
		return getListContainers(name).stream().anyMatch(container -> container.getState().contentEquals("running"));
	}

	public boolean runContainer(List<String> environments, Map<String, String> volumes, Map<Integer, Integer> ports, String name, List<String> commands) throws DockerClientException {
		if (Objects.isNull(environments)) {
			environments = new ArrayList<>();
		}
		if (Objects.isNull(volumes)) {
			volumes = new HashMap<>();
		}
		if (Objects.isNull(ports)) {
			volumes = new HashMap<>();
		}
		if (Objects.isNull(commands)) {
			commands = new ArrayList<>();
		}
		List<Bind> binds = volumes.entrySet().stream()
				.map(volume -> new Bind(volume.getKey(), new Volume(volume.getValue())))
				.collect(Collectors.toList());

		List<PortBinding> portBindings = ports.entrySet().stream()
				.map(portBinding -> new PortBinding(Binding.bindPort(portBinding.getKey()), ExposedPort.tcp(portBinding.getValue())))
				.collect(Collectors.toList());

		HostConfig hostConfig =  HostConfig.newHostConfig().withBinds(binds).withPortBindings(portBindings);

		CreateContainerResponse container = dockerClient.createContainerCmd(imageName + ":" + tag)
				.withEnv(environments).withHostConfig(hostConfig)
				.withHostName(name).withName(name)
				.withCmd(commands).exec();
		dockerClient.startContainerCmd(container.getId()).exec();
		return true;
	}

    public void removeContainer(String name) {
	    getListContainers(name).forEach(container -> dockerClient.removeContainerCmd(container.getId()).exec());
    }

	public void waitStarted(String name) throws DockerClientException {
		while (true) {
			if (getListContainers(name).stream()
					.filter(container -> container.getState().contentEquals("running"))
					.anyMatch(container -> !container.getStatus().contains("starting"))) {
				break;
			}
			log.debug("Waiting to get started container (" + name + ").");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new DockerClientException("It failed to run container (" + name + ") .", e);
			}
		}
	}
}
