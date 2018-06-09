SUMMARY = "RT2870 firmware"
LICENSE = "CLOSED"

SRC_URI = "file://rt2870.bin"

PR = "r1"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0755 rt2870.bin ${D}${base_libdir}/firmware
}

FILES_${PN} = "${base_libdir}"

