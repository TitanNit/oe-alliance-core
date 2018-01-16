SUMMARY = "Base packages require for image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

ALLOW_EMPTY_${PN} = "1"

PV = "1.0"
PR = "r16"

inherit packagegroup

RDEPENDS_${PN} = "\
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
	dvbsnoop \
	e2fsprogs-e2fsck \
	e2fsprogs-mke2fs \
	e2fsprogs-tune2fs \
	early-configure \
	ethtool \
	exteplayer3 \
	fakelocale \
	fuse-exfat \
	glibc-gconv-iso8859-15 \
	glib-networking \
	kernel-module-usbserial \
	kernel-module-ftdi-sio \
	kernel-module-pl2303 \
	kernel-module-belkin-sa \
	kernel-module-keyspan \
	kernel-module-tun \
	libdreamdvd \
	libdvdcss \
	libusb1 \
	mc \
	minidlna \
	mjpegtools \
	module-init-tools-depmod \
	modutils-loadscript \
	mtd-utils \
	nfs-utils \
	nfs-utils-client \
	ntfs-3g \
	ntpdate \
	ofgwrite \
	openssl \
	openvpn \
	opkg \
	packagegroup-core-boot \
	packagegroup-base-smbfs-client \
    packagegroup-base-smbfs-server \
    packagegroup-base-smbfs-utils \
    packagegroup-base-nfs \    
	parted \
	pngquant \
	procps \
	portmap \
	portmap-utils \
	rtmpdump \
	samba \
	sdparm \
	smbclient \
	smbnetfs \
	strace \
	titannit-bootlogo \
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
	\	
	${@base_conditional('MACHINE', 'dm800', '', 'mtd-utils-ubifs', d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "dvbc-only", "", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "iniwol", "ini-coldboot ini-ethwol", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "libpassthrough", "libpassthrough libdlsym", "", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "no-nmap", "" , "nmap", d)} \
	${@bb.utils.contains("MACHINE_FEATURES", "singlecore", "", \
	" \
	", d)} \
	${@bb.utils.contains("TARGET_ARCH", "sh4", "alsa-utils-amixer-conf" , "", d)} \
	titan-libeplayer3 \
    "    
