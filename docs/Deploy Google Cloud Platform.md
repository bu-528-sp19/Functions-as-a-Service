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

### Use Google Cloud Platform
The GCP status can also be checked using the GCP website
The running services can be checked at
https://console.cloud.google.com/kubernetes/discovery?authuser=2&organizationId=669079940348&project=tszlam-host-vpc&service_list_tablesize=50

And the VM instances can be checked at
https://console.cloud.google.com/compute/instances?authuser=2&organizationId=669079940348&project=tszlam-host-vpc&instancessize=50
Also SSH is enabled here. Simply click on the SSH button would do the job.
