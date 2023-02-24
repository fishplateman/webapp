resource "aws_vpc" "example_vpc" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "example-vpc"
  }
}

resource "aws_security_group" "example_sg" {
  name   = "example_web_app_sg"
  description = "Security group for web application load balancer"
  vpc_id = aws_vpc.example_vpc.id

  ingress {
    from_port      = 22
    to_port        = 22
    protocol       = "TCP"
    cidr_blocks    = ["0.0.0.0/0"]
  }

  ingress {
    from_port      = 80
    to_port        = 80
    protocol       = "TCP"
    cidr_blocks    = ["0.0.0.0/0"]
  }

  ingress {
    from_port      = 443
    to_port        = 443
    protocol       = "TCP"
    cidr_blocks    = ["0.0.0.0/0"]
  }

  # Ingress rule to any port on which the application runs
  ingress{
    from_port      = 0
    to_port        = 65535
    protocol       = "TCP"
    cidr_blocks    = ["0.0.0.0/0"]
  }

  egress {
    from_port      = 0
    to_port        = 0
    protocol       = "-1"
    cidr_blocks    = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "example_ec2" {
  ami           = data.aws_ami.example_ami.id
  instance_type = "t2.micro"
  key_name      = "example_keypair"
  subnet_id     = element(aws_subnet_ids.example_subnets, 0)
  security_groups = [aws_security_group.example_sg.name]

  provisioner "file" {
    source      = "./init_app.sh"
    destination = "/tmp/init_app.sh"
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_ebs_volume" "example_volume" {
  availability_zone = "us-east-1a"
  size              = 1
  type              = "gp2"
  tags = {
    Name = "example_volume"
  }
  lifecycle {
    ignore_changes = ["tags"]
    prevent_destroy = true
  }
  depends_on  = [aws_instance.example_ec2]

  attachment {
    device_name  = aws_instance.example_ec2.root_block_device[0]. device_name
    volume_id    = aws_ebs_volume.example_volume.id
    letSkipFinalSnapshot = false
  }
}