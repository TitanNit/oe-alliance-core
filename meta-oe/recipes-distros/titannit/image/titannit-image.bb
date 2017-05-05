SUMMARY = "TitanNit Image"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
MAINTAINER = "TitanNit team"

require conf/license/license-gplv2.inc

PV = "${IMAGE_VERSION}"
PR = "r${DATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup
#    ${DISTRO}-version-info
# FIX distro-image.bb ERROR: Taskhash mismatch - part 1 add packages to build dependencies of distro-image.bb which run on end of build process
DEPENDS = " \
    ${DISTRO}-base \
    titan-bin \
    "

# FIX distro-image.bb ERROR: Taskhash mismatch - part 2  make sure all do_rm_work tasks of build dependencies are finished before starting do_rootfs of distro-image.bb
do_rootfs[deptask] = "do_rm_work"

IMAGE_INSTALL = "\
	${DISTRO}-base \
    "

IMAGE_INSTALL_append_mipsel = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	${@base_contains('MACHINE', 'disabled-ipv6', '', 'kernel-module-ipv6', d)} \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	${@base_contains('MACHINE', 'disabled-ath9k', '', 'kernel-module-ath9k-htc', d)} \
	kernel-module-rt2800usb \
	${@base_contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)} \
	${@base_contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)} \
	rt3070 \
	rt8812au \
	rt8723a \
	${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
	${@base_contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)} \
	titan-gmediarender \
    "

IMAGE_INSTALL_append_arm = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	${@base_contains('MACHINE', 'disabled-ipv6', '', 'kernel-module-ipv6', d)} \
	kernel-module-ath9k \
	kernel-module-carl9170 \
	${@base_contains('MACHINE', 'disabled-ath9k', '', 'kernel-module-ath9k-htc', d)} \
	kernel-module-rt2800usb \
	${@base_contains('MACHINE', 'disabled-wlanbuild', '', 'kernel-module-rtl8192cu', d)} \
	${@base_contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)} \
	rt3070 \
	rt8812au \
	rt8723a \
	${@base_contains("MACHINE_FEATURES", "wifiusblegacy", "rtl871x", "kernel-module-r8712u", d)} \
	${@base_contains('MACHINE', 'disabled-build', '', 'packagegroup-base', d)} \
	titan-gmediarender \
    "

IMAGE_INSTALL_append_sh4 = "\
	firmware-rtl8192cu \
	firmware-rt2870 \
	firmware-rt3070 \
	firmware-atheros-ar9271 \
	firmware-carl9170 \
	firmware-htc9271 \
	firmware-htc7010 \
	firmware-rtl8712u \
	firmware-rtl8192eu \
	kernel-module-rt2800usb \
	${@base_contains("MACHINE_FEATURES", "linuxwifi", "kernel-module-rtl8xxxu", "rtl8192eu", d)} \
	rt3070 \
	rt8812au \
	rt8723a \
    "
#	titannit-version-info

#
#    python
#    python-codecs
#    python-compression
#    python-core
#    python-crypt
#    python-fcntl
#    python-lang
#    python-netclient
#    python-netserver
#    python-pickle
#    python-re
#    python-shell
#    python-threading
#    python-twisted-core
#    python-twisted-web
#    python-utf8-hack
#    python-xml
#    python-zlib
#    python-zopeinterface
#    python-email
#    python-mime
#    python-pyusb
#    python-subprocess
#    python-process
#    python-imaging
#	${@base_contains("MACHINE_FEATURES", "dreambox", "", "ofgwrite", d)} \
# disable for packagegroup-base
#	libcrypto-compat-0.9.7
#	libcrypto-compat-0.9.8
# disable for dm7200hd
#	kernel-module-rtl8192cu
# disabled building on svn
#	${@base_contains('MACHINE', 'vusolo2', 'titan-xbmc-helix', '', d)} \
#	${@base_contains('MACHINE', 'inihdp', 'titan-xbmc-nightly', '', d)} \
#    snes9x-sdl
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

do_install[vardepsexclude] += "DATETIME"

image_preprocess() {
    curdir=$PWD
    cd ${IMAGE_ROOTFS}

    # because we're so used to it
    ln -s opkg usr/bin/ipkg || true
    ln -s opkg-cl usr/bin/ipkg-cl || true

    cd $curdir

    # Speedup boot by reducing the host key size. The time it takes grows
    # exponentially by key size, the default is 2k which takes several
    # seconds on most boxes.
    echo 'DROPBEAR_RSAKEY_ARGS="-s 1024"' >> ${IMAGE_ROOTFS}${sysconfdir}/default/dropbear
}

IMAGE_PREPROCESS_COMMAND += "image_preprocess; "
