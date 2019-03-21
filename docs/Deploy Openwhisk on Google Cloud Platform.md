# Deploy Openwhisk on Google Cloud Platform



## Check the faas on GCP Project 

Steps are the same as GCP dployment

### Check the project
```
kubectl config use-context gke_tszlam-host-vpc_us-east1-b_faas
```

By now, the project is setup and can be accessed.
To swich back to the local minikube service, use kubectl config use-context will do.


***
Note:
If only to use a already deployed Openwhisk on GKE. Please go down to the last part 'Use an already deployed Openwhisk'
***


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

## Deploy Openwhisk

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

For this time labeling node is NOT NECESSARY. The nodes are already labeled.

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
Deploy Openwhisk
```
helm install ./helm/openwhisk --namespace=openwhisk --name=owdev -f myclusterGCP.yaml
```

Check the status of the deployment
```
helm status owdev
```

Once the Install-pakcages pod is finished, run the test
```
helm test owdev
```
if the tests passed, the deployment is finished

### Notes: Redeployment
Often times the deployment cannot be finished due to various reasons. During these circumstances, usually doint a helm del and redo the deployment will help

To delete the current owdev
```
helm del --purge owdev
```
And then redo the install
```
helm install ./helm/openwhisk --namespace=openwhisk --name=owdev -f myclusterGCP.yaml
```

### Notes: Adjust Runtime
Sometimes the install-packages pod will run for a long time. It could be optimized if the runtime.json file is adjusted so that some langauges/packages that are not used will not be installed.

The file is under the openwhisk git repo, and under /helm/openwhisk/runtimes.json.

In the file, you could see that for example "php", "swift" etc are all installed. These can be deleted to speed up the deployment time.

### Set up wsk actions
Now the wsk apihost name  should also be updated. But the wsk auth is still the same.
```
wsk -i property set --apihost openwhisk.faas.compulty.com:31001
wsk -i property set --auth 23bc46b1-71f6-4ed5-8c54-816aa4f8c502:123zO3xZCLrMN6v2BKK1dXYFpXlPkccOFqm12CdAsMgRU4VrNZ9lyGVCGuMDGIwP
 ```
### Create wsk actions and test
Now we can create a simple python file to test the deployment.
Create a hello.py:
```
def main(args):
    name = args.get("name","stranger")
    greeting = "hello " + name
    print(greeting)
    return {"greeting":greeting}
```

This python file can be deployed as a whisk action by
```
wsk -i action create hello hello.py
```

And to invoke the action simply use
```
wsk -i action invoke --result hello --param name World
```

The result would be 
```
{
    "greeting": "hello World"
}
```

Now we know the Openwhisk deployment is ready.


## Use an already deployed Openwhisk

Once the Openwhisk is already set up, for usage purposes there is actually no need to redo the process again. Simply following this part and then the Openwhisk on GCP can be used.

### Set up kubectl config
First step is to switch the working kubenetes
```
kubectl config get-contexts
kubectl config use-context gke_tszlam-host-vpc_us-east1-b_faas
```

### Set up wsk
```
wsk -i property set --apihost openwhisk.faas.compulty.com:31001
```

And now Openwhisk on GCP is ready!
