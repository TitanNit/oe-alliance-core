DESCRIPTION = "MME host library"

require ddt-apps.inc

DEPENDS += "jpeg"
PR = "r0"

do_install_append () {
	install -d ${D}${includedir}/libmmehost
#	install -m 644 ${S}/libmme_host/*.h ${D}${includedir}/libmmehost
}

FILES_${PN} += "${libdir}/libmmehost.so"
FILES_${PN}-dev = "${includedir}/libmme_host ${libdir}/libmmehost.la"

INSANE_SKIP_${PN} += "dev-so"

