#!/bin/sh
# configure SSH for ansible for the CI/CD

# get the VM IP
echo "IP VM front: "
read ip_front

# upload my ssh key to the VM
echo "UPLOAD THE KEY TO THE VM"
ssh-copy-id root@$ip_front

# create the production files
echo "
[front]
root@$ip_front

[all:vars]
ansible_connection=ssh
ansible_python_interpreter=/usr/bin/python
" > hosts
