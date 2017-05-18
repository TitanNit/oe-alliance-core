<<<<<<< HEAD
SRCDATE = "20170209"
=======
SRCDATE = "20161011"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d

require vuplus-initrd-cfe.inc

inherit deploy
do_deploy() {
    if [ -e vmlinuz-initrd-7439b0 ]; then
    install -m 0644 vmlinuz-initrd-7439b0 ${DEPLOYDIR}/initrd_auto.bin
    fi
}

addtask deploy before do_build after do_install

<<<<<<< HEAD
SRC_URI[md5sum] = "2b972c86995ad48212a25386966658a4"
SRC_URI[sha256sum] = "c383a7f67d05655c54822b1f7a89e54601831efdb530ecb6b903320e9539e9fa"
=======
SRC_URI[md5sum] = "4f25e392d807d95c8ddf65b55555a4ab"
SRC_URI[sha256sum] = "9286713a2d2b6cf65ee5926f2d26ff44089b344f208d801bf9687b38125fb71b"
>>>>>>> 4f58c88e9d0ec24aca8360de76c49e6d42d1d72d
