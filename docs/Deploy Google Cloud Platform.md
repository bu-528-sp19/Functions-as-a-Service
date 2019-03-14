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

