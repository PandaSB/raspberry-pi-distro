FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://ssh_host_dsa_key \
    file://ssh_host_dsa_key.pub \
    file://ssh_host_ecdsa_key \
    file://ssh_host_ecdsa_key.pub \
    file://ssh_host_ed25519_key \
    file://ssh_host_ed25519_key.pub \
    file://ssh_host_rsa_key \
    file://ssh_host_rsa_key.pub \
"


do_install_append () {
	install -m 0755 -d ${D}/var/log
	install -m 0755 -d ${D}/var/run
	install -m 0755 -d ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_dsa_key ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_dsa_key.pub ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_ecdsa_key ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_ecdsa_key.pub ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_ed25519_key ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_ed25519_key.pub ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_rsa_key ${D}/${sysconfdir}/ssh
	install -m 0600 ${WORKDIR}/ssh_host_rsa_key.pub ${D}/${sysconfdir}/ssh
	echo "tmpfs                /var/log             tmpfs      defaults              0  0" >> ${D}/${sysconfdir}/fstab
	echo "tmpfs                /var/run             tmpfs      mode=0755,nodev,nosuid,strictatime 0  0" >> ${D}/${sysconfdir}/fstab
}
