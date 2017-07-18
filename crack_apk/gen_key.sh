#!/bin/sh

if [ "$1" == "" ]
then
	echo "Usage: ./gen_key.sh output_name"
	exit
fi

echo "keytool -genkey -keystore $1.keystore -keyalg RSA -validity 10000 -alias $1"
# keytool -genkey -keystore $1.keystore -keyalg RSA -validity 10000 -alias $1
keytool -genkey -alias $1.keystore -keyalg RSA -validity 40000 -keystore $1.keystore
# keytool -genkey -keystore $1.keystore -keyalg RSA -validity 40000 -alias $1

