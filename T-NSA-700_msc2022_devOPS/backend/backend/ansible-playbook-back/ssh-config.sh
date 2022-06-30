#!/bin/sh
# configure SSH for ansible for the CI/CD

# get the VM IP
echo "IP VM back: "
read ip_back

# upload my ssh key to the VM
echo "UPLOAD THE KEY TO THE VM"
ssh-copy-id root@$ip_back

# create the production files
echo "
[back]
root@$ip_back

[all:vars]
ansible_connection=ssh
ansible_python_interpreter=/usr/bin/python
" > hosts
