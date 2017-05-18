SUMMARY = "Utilities for transponder & dvb-s blindscan"
SECTION = "base"
PRIORITY = "optional"
RDEPENDS_${PN} = "ncurses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "CLOSED"
require conf/license/license-close.inc

PACKAGES = "vuplus-blindscan-dvbs-utils vuplus-blindscan-dvbs-utils-dbg vuplus-blindscan-dvbc-utils vuplus-blindscan-dvbc-utils-dbg"

PROVIDES += "virtual/blindscan-dvbs virtual/blindscan-dvbc"
RPROVIDES_vuplus-blindscan-dvbs-utils += "virtual/blindscan-dvbs"
RPROVIDES_vuplus-blindscan-dvbc-utils += "virtual/blindscan-dvbc"

SRC_URI = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}.tar.bz2"
SRC_URI_arm = "http://archive.vuplus.com/download/utils/vuplus-blindscan-utils-${PV}_arm.tar.bz2;name=arm"

<<<<<<< HEAD
PV = "4.3"
PV_arm = "4.7"
=======
PV = "4.2"
PV_arm = "4.6"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d
PR = "r10"

S = "${WORKDIR}/blindscan-utils"

INSANE_SKIP_${PN} = "already-stripped"
FILES_vuplus-blindscan-dvbs-utils = "${bindir}/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils = "${bindir}/tda1002x ${bindir}/ssh108* ${bindir}/tt3l10* ${bindir}/tt2l08* ${bindir}/bcm3148"

FILES_vuplus-blindscan-dvbs-utils-dbg = "${bindir}/.debug/*_blindscan"
FILES_vuplus-blindscan-dvbc-utils-dbg = "${bindir}/.debug/tda1002x ${bindir}/.debug/ssh108* ${bindir}/.debug/tt2l08* ${bindir}/.debug/bcm3148"

do_install() {
	install -d ${D}/${bindir}/
	for i in `find ${S} -type f -maxdepth 1`; do
		install -m 0755 $i ${D}/${bindir}/;
	done;
}

<<<<<<< HEAD
SRC_URI[md5sum] = "a8ca2f8ce06d37b7d01b729b1e4e4abb"
SRC_URI[sha256sum] = "ec9b5dd552e72a0d775a77212350b71f5ea6f3619687c40c2bf97b12c5d7abd9"

SRC_URI[arm.md5sum] = "003d552f3c62f84f00a9bad288e131be"
SRC_URI[arm.sha256sum] = "27c30a9e332032b525af5a506e1337d5f2e68124035f6907a440b1836e084aa6"
=======
SRC_URI[md5sum] = "8e5b2f437b8d3f2b112300ca2e1539d5"
SRC_URI[sha256sum] = "d9b9fcfcafb8f89ded56c2bdd4c7bc6639bfdd93b8c8b78a23a40f4fa6219c2b"

SRC_URI[arm.md5sum] = "f61f4096570ff8fe7ed4d46acc7ba6fb"
SRC_URI[arm.sha256sum] = "1a9e8fd98c8aace692a66e1150db55bad80a5274650d618d3ad91c38ec67dc55"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d
