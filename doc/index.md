# Building a WildFly server for the cloud

In order to provision a WildFly server for the cloud, you have first to integrate the [WildFly Maven plugin](https://github.com/wildfly/wildfly-maven-plugin/)
into the pom.xml file of your project.

The "package" goal of the plugin allows you to:

* Provision a WildFly server tailored to your needs.
* Incorporate custom content (keystores, properties files, ...).
* Execute CLI scripts.
* Deploy your application.

The generated server is then ready to be installed inside a Docker Image to be deployed in your cluster.

# Provisioning with the cloud feature-pack

The cloud feature-pack is to be provisioned along with WildFly Galleon feature-pack. This is configured in the ``feature-packs`` configuration option 
of the WildFly Maven plugin.

For example:

```xml
<feature-packs>
  <feature-pack>
    <location>org.wildfly:wildfly-galleon-pack:${version.wildfly}</location>
  </feature-pack>
  <feature-pack>
    <location>org.wildfly.cloud:wildfly-cloud-galleon-pack:${version.wildfly.cloud.galleon.pack}</location>
  </feature-pack>
</feature-packs>
```

For a complete plugin configuration see this [example](https://github.com/wildfly/wildfly-s2i/blob/main/test/test-app/pom.xml).

# Building a WildFly Image

## Building an application image from a local execution of the WildFly Maven plugin

Once the Maven plugin has been configured (see a complete [example](https://github.com/wildfly/wildfly-s2i/blob/main/test/test-app/pom.xml)), 
calling ``mvn clean package`` will output a WildFly server containing your deployment in ``<project>/target/server`` directory.
 
This server has then to be installed into the WildFly runtime image ([JDK11](https://quay.io/repository/wildfly/wildfly-runtime-jdk11) or 
[JDK 17](https://quay.io/repository/wildfly/wildfly-runtime-jdk17)). 

Use [this dockerfile example](https://github.com/wildfly/wildfly-s2i/blob/main/examples/docker-build/Dockerfile) 
to build an application image using docker.

With JDK11 runtime image:

```
docker build -t my-application-jdk11:latest .
```

With JDK17 runtime image:

```
docker build --build-arg runtime_image="quay.io/wildfly/wildfly-runtime-jdk17:latest" -t my-application-jdk17:latest .
```

## Building an image in the cluster thanks to the WildFly S2I Builder image and Helm charts

Using [WildFly Helm charts](https://github.com/wildfly/wildfly-charts) is the simplest way to initiate the build of your application image in the cloud. 

[JDK11](https://quay.io/repository/wildfly/wildfly-s2i-jdk11) or [JDK17](https://quay.io/repository/wildfly/wildfly-s2i-jdk17) 
WildFly S2I Builder images can be configured in your helm chart to build and deploy an application image.

[These examples](https://github.com/wildfly/wildfly-s2i/tree/main/examples) will guide you step by step in order to deploy your application image in the cloud.

# Specifics of the wildfly-cloud-galleon-pack

When using the cloud galleon feature-pack, the following content will get provisioned:
* Server [startup scripts](launch.md).
* [Automatic adjustment](layers.md) of WildFly Galleon layers to cope with the cloud execution environment.
* Automatic provisioning of the health subsystem allowing for server state monitoring (Liveness and Readiness probes).
* Automatic routing of server logs to the console

