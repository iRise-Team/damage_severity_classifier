# How to deploy to GCP virtual machine:

1.	install python and pip (python version 3.8)
2.	install dependencies in requirements.txt using: 'sudo pip3 install -r requirements.txt'
3.	download model then copy the .h5 file to this folder
4.	then run server using: 'sudo python3 run_server.py'
5.	to test the server, run make_request.py: 'sudo python3 make_request.py'. note: adjust the urls with ur vm external ip
