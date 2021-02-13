SUMMARY = "TitanNit is a fast Linux Framebuffer Gui"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

inherit gitpkgv

SRCREV = "${AUTOREV}"
PKGV = "2.0+svnr${GITPKGV}"
PV = "2.0+svnr${SRCPV}"
PR = "r3"


SRC_URI = "svn://sbnc.dyndns.tv/svn/tools;module=bouquet2m3u;protocol=http"

S = "${WORKDIR}/"

do_compile() {
	cd ${WORKDIR}/bouquet2m3u
	if [ ${TARGET_ARCH} = "sh4" ];then
		${CC} -Os -c GO_bouquet2m3u.c -o bouquet2m3u
	else
		${CC} -Os -c GO_bouquet2m3u.c -o bouquet2m3u -mhard-float	
	fi
}

FILES_${PN} = "/sbin"

do_install() {
	install -d ${D}/sbin
	install -m 0755 bouquet2m3u/bouquet2m3u ${D}/sbin/bouquet2m3u
}
do_install[vardepsexclude] += "DATETIME"
