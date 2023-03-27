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
echo "installing polkit..."
sudo amazon-linux-extras install -y epel
sudo yum install -y polkit
echo "chmod"
sudo chmod 777 /etc/systemd/system
echo "configure systemd"
sudo yum install systemd
cd /etc/systemd/system
sudo echo "[Unit]
Description=Web Application

[Service]
ExecStart=/bin/bash -c 'cd /tmp; sudo java -jar /tmp/demo-1.0-SNAPSHOT.jar -Dspring.config.location=/tmp/application.yml'
Restart=always
User=root

[Install]
WantedBy=multi-user.target
" >> webapp.service
sudo systemctl daemon-reload
sudo systemctl enable webapp

sudo mkdir /var/logs/
sudo chmod 777 /var/logs/
sudo touch /var/logs/csye6225.log
sudo chown ec2-user /var/logs/csye6225.log
sudo chgrp ec2-user /var/logs/csye6225.log
sudo chmod 664 /var/logs/csye6225.log

cd /
sudo yum install -y amazon-cloudwatch-agent
cloudwatch_agent_installed=$(systemctl list-unit-files | grep amazon-cloudwatch-agent.service | wc -l)
sudo chmod 777 /opt
sudo mv /tmp/cloudwatch-config.json /opt/cloudwatch-config.json

sudo sed -i 's/^ExecStart=.*/ExecStart=\/bin\/bash -c '\''sudo \/opt\/aws\/amazon-cloudwatch-agent\/bin\/amazon-cloudwatch-agent-ctl -a fetch-config -m ec2 -c file:\/opt\/cloudwatch-config.json -s'\''/g' /etc/systemd/system/amazon-cloudwatch-agent.service
sudo sed -i 's/^Restart=.*/Restart=always/g' /etc/systemd/system/amazon-cloudwatch-agent.service

sudo systemctl daemon-reload
sudo systemctl enable amazon-cloudwatch-agent
sudo systemctl start amazon-cloudwatch-agent
