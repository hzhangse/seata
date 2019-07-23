/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.discovery.registry.kubernetes;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhanghao
 * @date 2019/5/19 17:05
 **/
public class KubernetesClientTest {

    @ClassRule
    public static KubernetesServer mockServer = new KubernetesServer(true,true);

    private static KubernetesClient mockClient;


    private static final String SERVICE_NAME = "default";



    @Before
    public void setup() {
        mockClient = new DefaultKubernetesClient();
//        mockClient = mockServer.getClient();
        // Configure the kubernetes master url to point to the mock server
        System.setProperty(Config.KUBERNETES_MASTER_SYSTEM_PROPERTY,
                mockClient.getConfiguration().getMasterUrl());
        System.setProperty(Config.KUBERNETES_TRUST_CERT_SYSTEM_PROPERTY, "true");
        System.setProperty(Config.KUBERNETES_AUTH_TRYKUBECONFIG_SYSTEM_PROPERTY, "false");
        System.setProperty(Config.KUBERNETES_AUTH_TRYSERVICEACCOUNT_SYSTEM_PROPERTY,
                "false");
    }

    @Test
    public void testClient() throws Exception {
        KubernetesRegistryServiceImpl kubernetesRegistryServic =  KubernetesRegistryServiceImpl.getInstance(mockClient);

        final List<InetSocketAddress> inetSocketAddressList = kubernetesRegistryServic.lookup("my_test_tx_group");

        assertThat(inetSocketAddressList).isNotEmpty();
    }
}
