SUMMARY = "A basic streming dev image for doing some camera"
HOMEPAGE = "http://www.sbarthelemy.com"
LICENSE = "MIT"

IMAGE_FEATURES += "package-management"
IMAGE_LINGUAS = "en-us"

inherit core-image

DEPENDS += "bcm2835-bootfiles"

CORE_OS = " \
    openssh openssh-keygen openssh-sftp-server \
    term-prompt \
    tzdata \
"

WIFI_SUPPORT = " \
    crda \
    iw \
    firmware \
    wireless-tools \
    wpa-supplicant \
"

DEV_SDK_INSTALL = " \
    binutils \
    binutils-symlinks \
    coreutils \
    cpp \
    cpp-symlinks \
    diffutils \
    file \
    g++ \
    g++-symlinks \
    gcc \
    gcc-symlinks \
    gettext \
    git \
    ldd \
    libstdc++ \
    libstdc++-dev \
    libtool \
    make \
    pkgconfig \
    python-modules \
"

DEV_EXTRAS = " \
    ntp \
    ntp-tickadj \
    serialecho  \
    spiloop \
"

EXTRA_TOOLS_INSTALL = " \
    bzip2 \
    ethtool \
    findutils \
    i2c-tools \
    iperf3 \
    iproute2 \
    less \
    netcat \
    procps \
    sysfsutils \
    tcpdump \
    unzip \
    util-linux \
    wget \
    zip \
"

MQTT = " \
    libmosquitto1 \
    libmosquittopp1 \
    mosquitto \
    mosquitto-dev \
    mosquitto-clients \
    python-paho-mqtt \
"

STREAMING = "\
    mjpg-streamer \
    mjpg-script \
"

IMAGE_INSTALL += " \
    ${CORE_OS} \
    ${DEV_SDK_INSTALL} \
    ${DEV_EXTRAS} \
    ${EXTRA_TOOLS_INSTALL} \
    ${WIFI_SUPPORT} \
    ${STREAMING} \
"

set_local_timezone() {
    ln -sf /usr/share/zoneinfo/EST5EDT ${IMAGE_ROOTFS}/etc/localtime
}

disable_bootlogd() {
    echo BOOTLOGD_ENABLE=no > ${IMAGE_ROOTFS}/etc/default/bootlogd
}

disable_unused_services() {
    rm ${IMAGE_ROOTFS}/etc/rc5.d/S15mountnfs.sh
}

set_wifi() {
  echo "ctrl_interface=/var/run/wpa_supplicant" > ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf 
  echo "ap_scan=1" >> ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf
  echo "network={"  >> ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf
  echo "    ssid=\"${WIFI_SSID}\"">> ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf
  echo "    psk=\"${WIFI_PASSWD}\"">> ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf
  echo "}" >> ${IMAGE_ROOTFS}/etc/wpa_supplicant.conf
  sed -i -e "s/#auto wlan0/auto wlan0/g" ${IMAGE_ROOTFS}/etc/network/interfaces
  sed -i -e "s/login=\"admin\"/login=\"${CAMERA_LOGIN}\"/g" ${IMAGE_ROOTFS}/etc/init.d/mjpg_streamer
  sed -i -e "s/password=\"admin\"/password=\"${CAMERA_PASSWD}\"/g" ${IMAGE_ROOTFS}/etc/init.d/mjpg_streamer
}

ROOTFS_POSTPROCESS_COMMAND += " \
    set_local_timezone ; \
    disable_bootlogd ; \
    disable_unused_services ; \
    set_wifi ; \
 "

export IMAGE_BASENAME = "iot-camera-image"
