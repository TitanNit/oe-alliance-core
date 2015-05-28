SUMMARY = "TitanNit Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATETIME}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

IMAGE_INSTALL = "\
    aio-grab \
    alsa-conf \
    alsa-utils \
    autofs \
    avahi-daemon \
    bash \
    curl \
    curlftpfs \
    djmount \
    dropbear \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-tune2fs \
    early-configure \
    ethtool \
    fakelocale \
    fuse-exfat \
    glibc-gconv-iso8859-15 \
    glib-networking \
    kernel-module-usbserial \
    kernel-module-ftdi-sio \
    kernel-module-pl2303 \
    kernel-module-belkin-sa \
    kernel-module-keyspan \
    kernel-module-ipv6 \
    libcrypto-compat-0.9.7 \
    libcrypto-compat-0.9.8 \
    libdreamdvd \
    libdvdcss \
    libusb1 \
    mjpegtools \
    module-init-tools-depmod \
    modutils-loadscript \
    mtd-utils \
    nfs-utils \
    nfs-utils-client \
    ntfs-3g \
    ntpdate \
    openssl \
    opkg \
    packagegroup-core-boot \
    parted \
    pngquant \
    procps \
    portmap \
    portmap-utils \
    python-codecs \
    python-core \
    python-crypt \
    python-fcntl \
    python-html \
    python-lang \
    python-netclient \
    python-netserver \
    python-pickle \
    python-re \
    python-shell \
    python-threading \
    python-twisted-core \
    python-twisted-web \
    python-utf8-hack \
    python-xml \
    python-zlib \
    python-zopeinterface \
    python-email \
    python-mime \
    python-pyusb \
    python-subprocess \
    python-process \
    python-imaging \
    rt8812au \
    rt8723a \
    rtmpdump \
    sdparm \
    smbclient \
    smbnetfs \
    sambaserver \
    strace \
    titannit-bootlogo \
    titannit-version-info \
    titan-gmediarender \
    titan-rarfs \
    tuxtxt-enigma2 \
    tzdata tzdata-europe tzdata-australia tzdata-asia tzdata-pacific tzdata-africa tzdata-americas \
    util-linux-blkid \
    util-linux-sfdisk \
    volatile-media \
    vsftpd \
    wakelan \
    wireless-tools \
    wpa-supplicant \
	\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
    kernel-module-rt2800usb \
    rt8812au \
    rt8723a \
	rtl8192cu \
    rt3070 \
	rt8812au \
	\	
    ${@base_conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
    ${@base_contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
    ${@base_contains("MACHINE_FEATURES", "dvbc-only", "", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
    ${@base_contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
    ${@base_contains("MACHINE_FEATURES", "singlecore", "", \
    " \
    packagegroup-base-smbfs \
    packagegroup-base-nfs \
    ", d)} \
    ${@base_contains("TARGET_ARCH", "sh4", "alsa-utils-amixer-conf" , "", d)} \
    ${@base_contains("TARGET_ARCH", "sh4", "libmmeimage " , "", d)} \
	titan-bin \
	${@base_contains('MACHINE', 'vusolo2', 'titan-xbmc-helix', '', d)} \
	${@base_contains('MACHINE', 'inihdp', 'titan-xbmc-nightly', '', d)} \
    "
# disabled building on svn
#    libavahi-client
#    minidlna
#    ${@base_contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    ${@base_conditional('MACHINE', 'inihdp', '', 'titan-xbmc', d)} \
#    ${@base_contains('MACHINEBUILD', 'atemionemesis', '', 'titan-xbmc', d)} \
#    titan-bin
#    ${@base_contains("TARGET_ARCH", "mipsel", "gst-plugin-libxt" , "", d)} \
#    titan-plugins
#    enigma2-locale-meta test fpr glibc only

export IMAGE_BASENAME = "titannit-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

rootfs_postprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true

    cd $curdir
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_postprocess; "

export NFO = '${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfo'

do_generate_nfo() {
    VER=`grep Version: "${IMAGE_ROOTFS}/etc/version"`
    echo "TitanNit: ${VER}" > ${NFO}
    echo "Machine: ${MACHINE}" >> ${NFO}
    DATE=`date +%Y-%m-%d' '%H':'%M`
    echo "Date: ${DATE}" >> ${NFO}
    echo "Issuer: openATV" >> ${NFO}
    echo "Link: ${DISTRO_FEED_URI}" >> ${NFO}
    if [ "${DESC}" != "" ]; then
            echo "Description: ${DESC}" >> ${NFO}
            echo "${DESC}" >> ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.desc
    fi
    MD5SUM=`md5sum ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.nfi | cut -b 1-32`
    echo "MD5: ${MD5SUM}" >> ${NFO}
}

addtask generate_nfo after do_rootfs
