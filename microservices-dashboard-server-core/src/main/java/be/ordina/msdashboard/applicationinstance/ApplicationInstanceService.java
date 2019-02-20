/*
 * Copyright 2015-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package be.ordina.msdashboard.applicationinstance;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.actuate.health.Status;
import org.springframework.cloud.client.ServiceInstance;

/**
 * Application service to interact with an {@link ApplicationInstance application instance}.
 *
 * @author Tim Ysewyn
 * @author Steve De Zitter
 */
public class ApplicationInstanceService {

	private final ApplicationInstanceRepository repository;

	public ApplicationInstanceService(ApplicationInstanceRepository repository) {
		this.repository = repository;
	}

	public Optional<ApplicationInstance> getApplicationInstanceForServiceInstance(ServiceInstance serviceInstance) {
		return Optional.ofNullable(this.repository.getById(serviceInstance.getInstanceId()));
	}

	public ApplicationInstance createApplicationInstanceForServiceInstance(ServiceInstance serviceInstance) {
		return this.repository.save(ApplicationInstance.from(serviceInstance));
	}

	public List<ApplicationInstance> getApplicationInstances() {
		return this.repository.getAll();
	}

	public void updateHealthStatusForApplicationInstance(String applicationInstanceId, Status healthStatus) {
		ApplicationInstance instance = this.repository.getById(applicationInstanceId);
		instance.updateHealthStatus(healthStatus);
		this.repository.save(instance);
	}

}
