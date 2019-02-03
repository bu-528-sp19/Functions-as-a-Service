# deploy OpenWhisk over Kubernetes

## introduction of Kubernetes
Kubernetes is a container orchestration platform that automates the deployment, scaling, and management of containerized applications.  
Here's a useful tutorial for you to get everything you need to know about kubernetes.  
https://kubernetes.io/docs/tutorials/kubernetes-basics/  

## introduction of OpenWhisk
Apache OpenWhisk (Incubating) is an open source, distributed Serverless platform that executes functions (fx) in response to events at any scale. OpenWhisk manages the infrastructure, servers and scaling using Docker containers so you can focus on building amazing and efficient applications.  
Being an open-source project, OpenWhisk stands on the shoulders of giants, including Nginx, Kafka, Docker, CouchDB. All of these components come together to form a “serverless event-based programming service”.  
The diagram below depicts the high-level architecture of OpenWhisk. From Nginx to Kafka to Docker, multiple technologies are powering Apache OpenWhisk which shows its committment to be a true Open Source Serverless Cloud Platform.  
<img src=".\images\OpenWhisk_flow_of_processing.png" width="50%" height="50%">

## why deploying OpenWhisk over Kubernetes?
Deploying OpenWhisk over Kubernetes can leverage the capabilities provided by Kubernetes to better control and manage OpenWhisk containers, which can result in a stable OpenWhisk runtime.  
<img src=".\images\kube-openwhisk.png" width="50%" height="50%">  
We can consider each module of OpenWhisk as an containerized application to be running on Kubernetes nodes.

## deploy OpenWhisk on Kubernetes by Helm  
Helm is a tool for managing Kubernetes charts, while charts are packages of pre-configured Kubernetes resources. In other words, user can write charts, which are in template format, to define a set of Kubernetes resources (each resource stands for a component of your application), and use Helm to deploy the charts over a Kubernetes cluster. 