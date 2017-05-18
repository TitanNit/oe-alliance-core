DESCRIPTION = "FFMpeg Player"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r0"

do_install_append () {
	install -d ${D}${includedir}/libeplayer3
	install -m 644 ${S}/libeplayer3/*.h ${D}${includedir}/libeplayer3
	echo ${S}/libeplayer3
#	exitt
}

FILES_${PN} += "${libdir}/libeplayer3.so"
FILES_${PN}-dev = "${includedir}/libeplayer3 ${libdir}/libeplayer3.la"

INSANE_SKIP_${PN} += "dev-so"

