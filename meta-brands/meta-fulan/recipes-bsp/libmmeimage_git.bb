DESCRIPTION = "MME image library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r0"

do_install_append () {
	install -d ${D}${includedir}/libmmeimage
	install -m 644 ${S}/libmme_image/*.h ${D}${includedir}/libmmeimage
	echo ${S}/libmmeimage
#	exitt
}

FILES_${PN} += "${libdir}/libmmeimage.so"
FILES_${PN}-dev = "${includedir}/libmme_image ${libdir}/libmmeimage.la"

INSANE_SKIP_${PN} += "dev-so"

