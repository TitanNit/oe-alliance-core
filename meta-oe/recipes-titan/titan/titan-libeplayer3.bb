SUMMARY = "libeplayer3 mediaplayer lib and console"
DESCRIPTION = "Core of movie player for Titan the libeplayer using the ffmpeg solution"
MAINTAINER = "TitanNit Team"
SECTION = "multimedia"

LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

#inherit autotools pkgconfig
inherit pkgconfig

SRCREV = "${AUTOREV}"
PKGV = "2.0+git${GITPKGV}"
PV = "2.0+gitr${SRCPV}"
PR = "r3"

SRC_URI = "svn://sbnc.dyndns.tv/svn/titan;module=libeplayer3;protocol=http"
#SRC_URI="git://github.com/titannit/exteplayer3.git;protocol=https"

DEPENDS = "ffmpeg libbluray libass"
RDEPENDS_${PN} = "ffmpeg libbluray libass"

inherit gitpkgv upx-compress


SSTATE_DUPWHITELIST += "${STAGING_DIR_TARGET}/usr/lib/libeplayer3.so.0.0.0"

S = "${WORKDIR}/libeplayer3"

CFLAGS_append = " \
	-I${S}/include \
	-I${S}/external \
    -I${S}/external/flv2mpeg4 \
	"

do_compile() {
	cd ${WORKDIR}/libeplayer3

	if [ ${HOST_SYS} = "sh4-oe-linux" ];then
		cp Makefile.am.sh4 Makefile.am
	elif [ ${HOST_SYS} = "arm-oe-linux-gnueabi" ];then
		cp Makefile.am.arm Makefile.am
	else
		cp Makefile.am.mipsel Makefile.am
	fi

	libtoolize --force
	aclocal -I ${STAGING_DIR_TARGET}/usr/share/aclocal
	autoconf
	automake --foreign --add-missing
	./configure --host=${HOST_SYS} --build=${BUILD_SYS}

	make -f Makefile
    	${STRIP} .libs/eplayer3
    	${STRIP} .libs/libeplayer3.so.0.0.0

	cp -a .libs/* ${STAGING_DIR_TARGET}/usr/lib/
}

FILES_${PN} = "/usr/bin"
FILES_${PN} += "/usr/lib"

do_install_append() {
    install -d ${D}${bindir}
    install -d ${D}${libdir}
    install -m 0755 .libs/eplayer3 ${D}${bindir}
    install -m 0755 .libs/libeplayer3.so.0.0.0 ${D}${libdir}/
    ln -s libeplayer3.so.0.0.0 ${D}${libdir}/libeplayer3.so
    ln -s libeplayer3.so.0.0.0 ${D}${libdir}/libeplayer3.so.0
}
