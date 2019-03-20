minikube config set kubernetes-version v1.10.5

minikube config set cpus 2

minikube config set memory 4096

minikube config set WantUpdateNotification false

minikube start

minikube ssh -- sudo ip link set docker0 promisc on

helm init

kubectl create clusterrolebinding tiller-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:default

kubectl label nodes --all openwhisk-role=invoker

helm install ./helm/openwhisk --namespace=openwhisk --name=owdev -f mycluster.yaml