#sudo apt install snapd

#sudo snap find microk8s

# install MicroK8s 
#sudo snap install microk8s --classic    
microk8s (1.27/stable) v1.27.2 from Canonical installed

# Enable addons
microk8s enable dns  
microk8s enable dashboard  
microk8s enable storage  

microk8s disable dns  
microk8s disable dashboard  
microk8s disable storage  
