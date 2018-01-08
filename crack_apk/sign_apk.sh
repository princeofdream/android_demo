#!/bin/sh

if [ "$1" == "" ]
then
	echo "Usage: ./sign_apk.sh apk_name"
	exit
fi


echo "jarsigner -verbose -keystore $1.keystore -signedjar $1-signed.apk $1.apk $1"
# jarsigner -verbose -keystore $1.keystore -signedjar $1-signed.apk $1.apk $1
jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore $1.keystore -signedjar $1-signed.apk $1.apk $1.keystore

# echo "jarsigner -digestalg SHA1 -sigalg MD5withRSA -tsa -verbose -keystore $1.keystore -signedjar $1-signed.apk $1.apk $1"
# jarsigner -digestalg SHA1 -sigalg MD5withRSA -tsa -verbose -keystore $1.keystore -signedjar $1-signed.apk $1.apk $1

