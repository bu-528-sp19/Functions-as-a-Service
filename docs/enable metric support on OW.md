# Enable Metric Support on OpenWhisk

This is a instruction about how to enable metrics support on OpenWhisk which is delopyed on k8s.

## Step1.

use helm to install prometheus-statsd-exporter:  
```
git clone https://github.com/erandiganepola/serverless-solution-monitoring.git
cd serverless-solution-monitoring
helm install ./prometheus-statsd-exporter --name=prometheus --namespace=monitoring
```

## Step2.
check the pod's name and service's name
```
kubectl get pods -n monitoring
kubectl get service -n monitoring
```

## Step3.
edit the yaml of daemonset: invoker and statefulset controller
```
# get the name of daemonset
kubectl get daemonset -n openwhisk
# edit the yaml
kubectl edit darmonset <the name of invoker> -n openwhisk -o yaml
```
This will put you in editing mode. Scroll down to *containers.env* section. There, you will find the other env variables being passed to the container.  Insert the following:  
```
- name: METRICS_KAMON
  value: "true"
- name: CONFIG_kamon_statsd_hostname
  value: <the service name you get from Step2.>.monitoring.svc.cluster.local
- name: CONFIG_kamon_statsd_port
  value: "9125"
```
Then, do the same for the controller: `kubectl edit stateful set <the name of controller> -n openwhisk -o yaml`. Insert the variables in the same manner.  
Then, you will see the invoker and controller pod start rebooting, wait until they are running. The metrics are now enabled.  

## Step 4.
you can use `kubectl port-forward <name of the pod you get from Step2.> -n monitoring 9102` to view the metrics from browser. After typing it, then go to `http://localhost:9102`

## Step 5.
Install Prometheus and Grafana on ubuntu. Add prometheus as data source and design the metrics dashboard for it.  