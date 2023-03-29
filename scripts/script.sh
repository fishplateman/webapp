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
echo "chmod"
sudo chmod 777 /etc/systemd/system
echo "configure systemd"
sudo yum install systemd
cd /etc/systemd/system
sudo echo "[Unit]
Description=Web Application

[Service]
ExecStart=/bin/bash -c 'cd /tmp; kill -9 $(lsof -t -i:8080); java -jar /tmp/demo-1.0-SNAPSHOT.jar -Dspring.config.location=/tmp/application.yml'
Restart=always
User=root

[Install]
WantedBy=multi-user.target
" >> webapp.service
sudo systemctl daemon-reload
sudo systemctl enable webapp

sudo mkdir /var/log/
sudo chmod 777 /var/log/
sudo touch /var/log/csye6225.log
sudo chown ec2-user /var/log/csye6225.log
sudo chgrp ec2-user /var/log/csye6225.log
sudo chmod 664 /var/log/csye6225.log

cd /
sudo yum install -y amazon-cloudwatch-agent
cloudwatch_agent_installed=$(systemctl list-unit-files | grep amazon-cloudwatch-agent.service | wc -l)
sudo chmod 777 /opt
sudo mv /tmp/cloudwatch-config.json /opt/cloudwatch-config.json

sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
-a fetch-config \
-m ec2 \
-c file:/opt/cloudwatch-config.json \
-s

sudo systemctl daemon-reload
sudo systemctl enable amazon-cloudwatch-agent