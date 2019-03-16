# Developer Google Cloud Platform

## Install Google Cloud Platform sdk

### Create an environment variable for the correct distribution:
```
export CLOUD_SDK_REPO="cloud-sdk-$(lsb_release -c -s)"
```
### Add the Cloud SDK distribution URI as a package source:
```
echo "deb http://packages.cloud.google.com/apt $CLOUD_SDK_REPO main" | sudo tee -a /etc/apt/sources.list.d/google-cloud-sdk.list
```
### Import the Google Cloud public key:
```
curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
```

### Update and install the Cloud SDK:
```
sudo apt-get update && sudo apt-get install google-cloud-sdk
```
Note: For additional apt-get options, such as disabling prompts or dry runs, refer to the apt-get man pages.

Optionally, install any of these additional components:
```
google-cloud-sdk-app-engine-python
google-cloud-sdk-app-engine-python-extras
google-cloud-sdk-app-engine-java
google-cloud-sdk-app-engine-go
google-cloud-sdk-datalab
google-cloud-sdk-datastore-emulator
google-cloud-sdk-pubsub-emulator
google-cloud-sdk-cbt
google-cloud-sdk-cloud-build-local
google-cloud-sdk-bigtable-emulator
kubectl
```
For example, the google-cloud-sdk-app-engine-java component can be installed as follows:
```
sudo apt-get install google-cloud-sdk-app-engine-java
```

## Initialize gcloud and Function-as-a-Service project on GCP

### Run gcloud init to get started:
```
gcloud init
```
After this, a log in webpage will pop out for you to log in the account used for google cloud platform.

### Set up Google Cloud Platform Default
```
gcloud config set project tszlam-host-vpc
gcloud config set compute/region us-east1
gcloud config set compute/zone us-east1-b
```

### Connect to project name tszlam-host-vpc
```
gcloud container clusters get-credentials faas --zone us-east1-b --project tszlam-host-vpc
```

### Check the project
```
kubectl config get-contexts
kubectl config use-context gke_tszlam-host-vpc_us-east1-b_faas
kubectl get nodes -o wide
```

By now, the project is setup and can be accessed.
To swich back to the local minikube service, use kubectl config use-context will do.

## Deploy a hello world app on GKE (Optional)

### Build the docker image
To download the hello-app source code, run the following commands:
```
git clone https://github.com/GoogleCloudPlatform/kubernetes-engine-samples
cd kubernetes-engine-samples/hello-app
```

Set the PROJECT_ID environment variable
```
export PROJECT_ID="$(gcloud config get-value project -q)"
```

To build the container image of this application and tag it for uploading, run the following command:
```
docker build -t gcr.io/${PROJECT_ID}/hello-app:v1 .
```
Check docker images:
```
docker images
```
Note: if no permission is granted, then sudo docker is needed

### Upload Docker Image
First, configure Docker command-line tool to authenticate to Container Registry (you need to run this only once):
```
gcloud auth configure-docker
```
You can now use the Docker command-line tool to upload the image to your Container Registry:
```
docker push gcr.io/${PROJECT_ID}/hello-app:v1
```
### Deploy application
un the following command to deploy your application, listening on port 8080:
```
kubectl run hello-web --image=gcr.io/${PROJECT_ID}/hello-app:v1 --port 8080
```
To see the Pod created by the Deployment, run the following command:
```
kubectl get pods
```

### Expose your application to the Internet
By default, the containers you run on GKE are not accessible from the Internet, because they do not have external IP addresses. You must explicitly expose your application to traffic from the Internet, run the following command:
```
kubectl expose deployment hello-web --type=LoadBalancer --port 80 --target-port 8080
```

### Cleaning up
To avoid incurring charges to your Google Cloud Platform account for the resources used in this tutorial:

After completing this tutorial, follow these steps to remove the following resources to prevent unwanted charges incurring on your account:

Delete the Service: This step will deallocate the Cloud Load Balancer created for your Service:
```
kubectl delete service hello-service
```

## Deploy Openwhisk (ongoing)

### helm init
start with the same helm instructions
```
helm init
kubectl create clusterrolebinding tiller-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:default
```

### label nodes
check the nodes
```
kubectl get nodes
```

label the wanted node to be the invoker
```
kubectl label nodes <INVOKER_NODE_NAME> openwhisk-role=invoker
```

### customize mycluster.yaml
Create another 'mycluster.yaml' file in the git reporsitory. 
To distinguish from the previous minikube one. Here the file will be names as myclusterGCP.yaml.
The contents are as follows:
```
whisk:
 ingress:
   apiHostName: openwhisk.faas.compulty.com
   apiHostPort: 31001
   type: NodePort
   annotations:
     kubernetes.io/ingress.class: nginx
     nginx.ingress.kubernetes.io/proxy-body-size: 0

nginx:
 httpsNodePort: 31001 
 ```
 
 
### Deploy openwhisk

```
helm install ./helm/openwhisk --namespace=openwhisk --name=owdev -f myclusterGCP.yaml
```

