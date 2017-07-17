#!/bin/bash 
# NDK=/home/lijin/Environment/Android/android-ndk-r13b
# SYSROOT=$NDK/platforms/android-21/arch-arm/
# TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64


function build_one
{
./configure \
    --prefix=$PREFIX \
    --enable-shared \
    --enable-static \
    --disable-gpac \
    --disable-cli \
    --extra-cflags="-Os -fpic $ADDI_CFLAGS" \
    --extra-ldflags="$ADDI_LDFLAGS" \
    $ADDITIONAL_CONFIGURE_FLAG \
    # --host=arm-linux \
    # --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
    # --sysroot=$SYSROOT \
}
CPU=x86
PREFIX=$(pwd)/../rootfs/$CPU
ADDI_CFLAGS=""

build_one
