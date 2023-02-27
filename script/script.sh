#ps -ef | grep yum | grep -v grep | awk '{print $2}' | xargs sudo kill -9
#!/bin/bash
#Pre-requisites: Install JDK, Mysql Server
# echo "installing maven"
# wget http://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
# echo "unzipping maven"
# tar -xzvf apache-maven-3.6.3-bin.tar.gz
# sudo rm -f   /var/run/yum.pid
sudo yum update -y 
echo "updating yum..."
sudo yum update
echo "upgrading yum..."
sudo yum upgrade -y
echo "cleaning yum..."
sudo yum clean all
echo "remove jdk..."
sudo yum remove -y java
echo "installing jdk..."
sudo yum install -y java-1.8.0-openjdk
echo "installing nginx"
sudo yum install nginx -y
echo "Installing Mysql Server..."
sudo yum install mysql-server
echo "Installing start Mysql Server..."
sudo systemctl start mysqld
echo "Installing git..."
sudo yum install git -y
echo "Installing maven..."
sudo yum install maven -y


#Set Env Variables
# echo "Setting JAVA_HOME env variable..."
# export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
# echo "Setting MYSQL_HOME env variable"
# export MYSQL_HOME=/var/lib/mysql 
# Configure MySQL server
echo "Configure MySQL server..."
mysql -u root -p 
# Change password
echo "Change password..." 
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'aabcd';
FLUSH PRIVILEGES; 
EXIT
#Download Spring Boot project from github
echo "Downloading webapp from Github..."
git clone git@github.com:AlatusKitty/webapp.git
#build the project
echo "Building the project"
cd webapp
mvn clean install 
#deploy the application
echo "Deploying the application"
mvn package
mvn spring-boot:run 
