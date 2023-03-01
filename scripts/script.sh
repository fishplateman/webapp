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
ExecStart=/bin/bash -c 'cd /tmp; java -jar /tmp/demo-1.0-SNAPSHOT.jar -Dspring.config.location=/tmp/application.yml'
Restart=always
User=root

[Install]
WantedBy=multi-user.target
" >> webapp.service
sudo systemctl daemon-reload
sudo systemctl enable webapp