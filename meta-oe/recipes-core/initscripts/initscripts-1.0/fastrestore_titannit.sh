echo "#### start ###############"
echo start fastrestore

startconfig=/mnt/config/start-config
if [ ! -e "$startconfig" ]; then startconfig="/etc/titan.restore/mnt/config/start-config"; fi

. $startconfig
. /sbin/start-progress
. /sbin/start-function


model=`cat /etc/model`
realbox=`cat /proc/stb/info/boxtype`
arch=`cat /etc/.arch`
board=`cat /etc/.board`

startwebif()
{
	if [ ! -L /var/usr/local/share/titan/web/tmp ];then
		echo "[$0] startwebif"
		rm -rf /var/usr/local/share/titan/web/tmp
		ln -s /tmp /var/usr/local/share/titan/web/tmp
	fi
}

startautofs()
{
		echo "[$0] startautofs"
		if [ ! -L /etc/auto.network ];then
			rm /etc/auto.network
			ln -s /mnt/network/auto.misc /etc/auto.network
		fi
}

startnetwork()
{
		echo "[$0] startnetwork $1"
		if [ ! -L /etc/network/interfaces ];then
			rm /etc/network/interfaces
			ln -s /mnt/network/interfaces /etc/network/interfaces
		fi
		if [ ! -L /etc/resolv.conf ];then
			rm /etc/resolv.conf
			ln -s /mnt/network/resolv.conf /etc/resolv.conf
		fi
		if [ "$1" == "restart" ];then /etc/init.d/networking restart;fi
}

startmnt()
{
	echo "[$0] startmnt"
	if [ -L /mnt ];then
		rm -f /mnt
#		startautofs reload
		startautofs
		startnetwork
		startwebif
#		sleep 10
	fi

	if [ -e /var/etc/.erasemtd ] || [ ! -e /mnt/swapextensions ]; then
		if [ -e /var/etc/.backupmtd ]; then
			mkdir /tmp/backupmtd
			cp -a /mnt/settings /tmp/backupmtd
			cp -a /mnt/config /tmp/backupmtd
			cp -a /mnt/network /tmp/backupmtd
			cp -a /mnt/script /tmp/backupmtd
			mkdir /tmp/backupmtd/swapextensions
			cp -a /mnt/swapextensions/player /tmp/backupmtd/swapextensions
			backuptpk
		fi

		if [ -e /media/hdd/.update ] && [ -e /var/etc/.firstboot ];then
			BACKUPDIR=/media/hdd/.update
			echo "[$0] startmnt: cp -a $BACKUPDIR /mnt"
			cp -a $BACKUPDIR /mnt
			mv -f $BACKUPDIR/.last $BACKUPDIR/.last.restored
			sync
		elif [ -e /var/backup/.update ] && [ -e /var/etc/.firstboot ];then
			BACKUPDIR=/var/backup/.update
			echo "[$0] startmnt: cp -a $BACKUPDIR /mnt"
			cp -a $BACKUPDIR /mnt
			mv -f $BACKUPDIR/.last $BACKUPDIR/.last.restored
			sync
		elif [ -e /var/swap/.update ] && [ -e /var/etc/.firstboot ];then
			BACKUPDIR=/var/swap/.update
			echo "[$0] startmnt: cp -a $BACKUPDIR /mnt"
			cp -a $BACKUPDIR /mnt
			mv -f $BACKUPDIR/.last $BACKUPDIR/.last.restored
			sync
		else
			infobox -pos -1 75% 10015 "MNT" "            Formatiere Laufwerk            " &
			if [ -e /mnt ];then
				echo "remove mnt files"
				rm -rf /mnt
			fi
			cp -a /etc/titan.restore/mnt /
			mkdir /mnt/swapextensions
			mkdir /mnt/bin
			mkdir /mnt/tpk
			sleep 10

			if [ -e /var/etc/.backupmtd ]; then
				restoretpk

				rm -rf /mnt/settings
				rm -rf /mnt/config
				rm -rf /mnt/network
				rm -rf /mnt/script
				rm -rf /mnt/swapextensions/player
				cp -a /tmp/backupmtd/* /mnt
#			else
#				mkdir /mnt/tpk
#				mkdir /mnt/script
#				reset.sh
			fi

			rm -r /var/etc/.erasemtd
			rm -r /var/etc/.backupmtd

			#ubifs needs sync
			sync

			#startMicomUpdate

		#	killall infobox
		#	infobox 9999 INFO "Initializing MNT" "" "you can power off the receiver now,"  "in case it does not reboot" &
		#	sleep 2
#			reboot
		fi
#		startnetwork restart
	fi
	rm /var/etc/.firstboot
}

startopkg()
{
	if [ $(cat /etc/opkg/opkg.conf | grep "dest / /" | wc -l) -eq 0 ];then
		echo "[$0] startopkg add /var"
		echo "dest /var /var" >> /etc/opkg/opkg.conf
	fi
	if [ $(cat /etc/opkg/opkg.conf | grep "dest /mnt/swapextensions /mnt/swapextensions" | wc -l) -eq 0 ];then
		echo "[$0] startopkg add /mnt/swapextensions"
		echo "dest /mnt/swapextensions /mnt/swapextensions" >> /etc/opkg/opkg.conf
	fi
	if [ $(cat /etc/opkg/opkg.conf | grep "dest /var/swap /var/swap" | wc -l) -eq 0 ];then
		echo "[$0] startopkg add /var/swap"
		echo "dest /var/swap /var/swap" >> /etc/opkg/opkg.conf
	fi
}

startplugins()
{
	if [ ! -L /var/usr/local/share/titan/plugins ];then
		echo "[$0] startplugins link plugins"
		rm -rf /var/usr/local/share/titan/plugins
		ln -sf /usr/local/share/titan/plugins /var/usr/local/share/titan/plugins
	fi
}

startmnt
startplugins
#starthotplug
startopkg

