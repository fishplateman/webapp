#!/bin/bash
echo "updating yum..."
sudo yum update -y
echo "upgrading yum..."
sudo yum upgrade -y
echo "cleaning yum..."
sudo yum clean all
echo "remove jdk..."
sudo rpm -qa | grep -i java | xargs -n1 rpm -e --nodeps
echo "installing jdk..."
sudo yum install -y java-1.8.0-openjdk