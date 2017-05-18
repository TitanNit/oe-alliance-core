<<<<<<< HEAD
SRCDATE = "20170209"
=======
SRCDATE = "20161011"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7445d0 ]; then
    install -m 0644 vmlinuz-initrd-7445d0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

<<<<<<< HEAD
SRC_URI[md5sum] = "eb886ac801f585b04cffc218c44b60bf"
SRC_URI[sha256sum] = "ec268609e4d062dd0e75d249445d41589e2d7cf68521642102cc8fd91c3a8161"
=======
SRC_URI[md5sum] = "997063e4f91d4b301fe476e65aca56a6"
SRC_URI[sha256sum] = "ab6d4b4364333817f0a47d1e07d2214e732f96c7bafa94ce56619ac27c68f0ef"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d
