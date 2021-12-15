# CS-GY 9053 I2 – Introduction to Java - Fall 2021

# Final Project: Chat software

## Description

This program allows multiple users to create an account in the group and chat privately with each other.

## How to use

### Server: 

* Download the entire file and open the config.perperties.
  * url is where the database located, to create the database you could use e.g. Navicat to import the database
  ![create database](/screenshot/1.gif)
  * username and pwd is the username and password for your mySQL useraccount, and you may need to change it to your own mySQL user.
  * mykey is used to encode the passwords of our users
* launch serverConn.java in the server folder
* Error may occur when connecting to the database. If that happens, you could try to reconfigure the mySQL authentication method to Legacy authentication method (retain MySQL 5.x compatibility)

### Client: 
* Download the entire file and open the configConnection, and change HOST to your server's IP address.
* launch loginInterface.java in the clientView folder

## Login

You could login with some default username and password already stored in the database. E.g.\
Username: Frank, Password frank;\
Username: Lucy, Password lucy;\
...

## Registration

Click the create an account button, then you could create an account with your own username and password.

## Chat
Double click the friend name label to open a chat.\
If both of clients open the chat, they can send messages to each other by clicking the send button.(Enter does not work here.)\
If A is trying to send a message to B that is offline, the server will notify A that your target B is offline.\
If both of clients are online, but B has not opened that chat window, B's friendname will turn into blue to notify her that she has a new message from A.

## Exit
Close the client before shutting down the server
