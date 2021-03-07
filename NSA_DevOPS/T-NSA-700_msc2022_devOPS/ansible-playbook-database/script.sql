CREATE USER 'ludger' IDENTIFIED WITH mysql_native_password BY 'ludger';
GRANT ALL PRIVILEGES ON *.* TO 'ludger';
FLUSH PRIVILEGES;
