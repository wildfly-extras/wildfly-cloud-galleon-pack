/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.cloud.test;

import java.io.IOException;
import org.junit.runner.RunWith;
import org.junit.Test;
import javax.inject.Inject;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import static org.junit.Assert.assertEquals;

import org.wildfly.core.testrunner.ManagementClient;
import org.wildfly.core.testrunner.WildflyTestRunner;

@RunWith(WildflyTestRunner.class)
public class SimpleTestCase {

    @Inject
    private ManagementClient managementClient;

    @Test
    public void test() throws IOException {
        ModelControllerClient client = managementClient.getControllerClient();
        final ModelNode operation = new ModelNode();
        operation.get("operation").set("read-resource");
        operation.get("address").set("/subsystem=jaxrs");
        ModelNode result = client.execute(operation);
        assertEquals(result.asString(), "success", result.get("outcome").asString());
    }
}
