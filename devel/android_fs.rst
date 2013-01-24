.. toctree::
   :numbered:
==================
Android Filesystem
==================


How it is different from general GNU/linux filesystem ?
=======================================================

Android is based on `linux kernel <www.kernel.org>`_, but its
filesystem hirarchy differs from conventional linux filesystem. Most
of the filesystem fall under ``/system``. Also this is the main OS
system. User's data is stored under ``/data`` directory. The
``/cache`` partition is mostly used by Android's Dalvik virtual
machine. ``default.prop`` contains default system property setting
which are restored at every bootup. ``/etc`` is symlink to
``/system/etc``. ``/dev/`` contains all device files. ``/proc``
Filesystem acts same as in linux. ``/sdcard`` is external SD-card
directory. 

**Android filesystem**
:: 

   /
   | -> acct/
   | -> cache/
   | -> config/
   | -> d/
   | -> data/
             | -> local/
   | -> default.prop
   | -> dev/
            | -> alarm
            | -> android_adb
            | -> ashmem
            | -> aw_i2c_ts1
            | -> binder
            | -> block
	    .
	    .
	    .         
   | -> etc/
            | -> apns-conf.xml
	    | -> audio_effects.conf
	    | -> bluetooth
	    | -> camera.cfg
	    | -> dbus.conf
	    | -> dhcpcd
	    .
	    .
	    .
   | -> init
   | -> init.goldfish.rc
   | -> init.rc
   | -> init.sun5i.rc
   | -> init.sun5i.usb.rc
   | -> initlogo.rle
   | -> mnt/
   | -> oem/
   | -> proc/
   | -> root/
   | -> sbin
   | -> sdcard/
   | -> sys/
   | -> system/
               | -> app
	       | -> bin/
	                | -> preinstall
	       | -> build.prop
	       | -> etc/
	       | -> fonts/
	       | -> framework/
	       | -> lib/
	       | -> media/
	       | -> preinstall/
   | -> ueventd.goldfish.rc
   | -> ueventd.rc
   | -> ueventd.sun5i.rc
   | -> vendor

Introduction to ``chroot``
===========================

.. sidebar:: code 

   example of chroot

   SYNTAX : ``chroot <chroot-dir> <shell>``
   ::
   
      chroot /linux /bin/bash

   running Xvfb

   ::
      
      nohup Xvfb :1 -screen 0 640x480x24 -ac < /dev/null > Xvfb.out 2>
      Xvfb.err &

Aakash Programming Language(APL) is a web based front end to four
programming languages C, C++, Python and Scilab. In the backend, we
run a webserver in a ``chroot'd`` environment. The ``chroot``
environment is a custom made GNU/Linux distribution without any
'X-server'. Instead of having a 'X-server' which is high on both CPU
and memory, we use a X virtual framebuffer called ``Xvfb``. ``Xvfb``
is low on both CPU and memory and requires no dedicated display
hardware for graphical display. All display of graphs(plots) in APL is
done using ``Xvfb``.

As the name says, ``CHROOT`` stands for `change root` which means,
user can decide the `root` or ``/`` directory for its environment. For
more information on chroot, Visit its ``man page``.  Chroot as its own
advantage, apart from what we have used in APL. For developer's, it
can be a good development environment to test their new software as it
is totally isolated from host machine.

