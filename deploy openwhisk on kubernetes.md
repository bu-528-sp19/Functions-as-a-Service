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

** **

## Mac configuration

### 1. Install Docker
Install Docker Desktop for Mac on the docker <a href="https://www.docker.com/products/docker-desktop">website</a>

### 2. Install kubernetes
Use Homebrew on macOS to install kubernetes
```
brew install kubernetes-cli
```
Test to ensure the version you installed is sufficiently up-to-date:
```
kubectl version
```

### 3. Install Minikube
Use Homebrew on macOS to install minikube
```
brew cask install minikube
```

### 4. Configure Minikube
Configure the minikube. With test, the kubernetes version of 1.10.5 works.
```
minikube config set kubernetes-version v1.10.5
minikube config set cpus 2
minikube config set memory 4096
minikube config set WantUpdateNotification false
```
Start Minikube
with minikube v0.25.2:
```
minikube start --extra-config=apiserver.Authorization.Mode=RBAC
```
with minikube versions more recent than v0.25.2:
```
minikube start
```
Setup Docker network in promiscuous mode (** important)
Put the docker network in promiscuous mode.
```
minikube ssh -- sudo ip link set docker0 promisc on
```
Test the ip of minikube node
```
minikube ip
```

### 5. OpenWhisk Deployment on Kubernetes
Install Helm by Homebrew
```
brew install kubernetes-helm
```
Helm init
```
helm init
kubectl create clusterrolebinding tiller-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:default
```
Initial setup to indicate the Kubernetes worker nodes that should be used to execute user containers by OpenWhisk's invokers. For a single node cluster, simply do
```
kubectl label nodes --all openwhisk-role=invoker
```
Download custome configuration file in OpenWhisk github
```
git clone https://github.com/apache/incubator-openwhisk-deploy-kube.git
```
Create a file named `mycluster.yaml` in the downloaded file directory and write in as below and save:
```
whisk:
  ingress:
    type: NodePort
    apiHostName: $(your_minikube_ip)
    apiHostPort: 31001

nginx:
  httpsNodePort: 31001
```
### 6. Deploy With Helm
Deployment can be done by using the following single command:
```
helm install ./helm/openwhisk --namespace=openwhisk --name=owdev -f mycluster.yaml
```
Use the below command line to inspect the status, once the `install-packages` Pod is in the `Completed` state, your OpenWhisk deployment is ready to be used.
```
helm status owdev
```
### 7. Configure the wsk CLI
If you haven't installed the wsk CLI, use Homebrew to install it firstly:
```
brew install wsk
```
If the wsk CLI is ready, configuring the CLI for Kubernetes on Docker for Mac
```
wsk property set --apihost localhost:<whisk.ingress.apiHostPort>
wsk property set --auth 23bc46b1-71f6-4ed5-8c54-816aa4f8c502:123zO3xZCLrMN6v2BKK1dXYFpXlPkccOFqm12CdAsMgRU4VrNZ9lyGVCGuMDGIwP
```
Verify the OpenWhisk Deployment
Issue the command below to run the basic verification test suite included in the OpenWhisk Helm chart.
```
helm test owdev
```


