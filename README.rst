=========================
GNUkhata on Aakash tablet
=========================

Porting of `GNUkhata <http://gnukhata.org/>`_ on `Aakash
<http://www.iitb.ac.in/AK/Aakash.htm>`_ tablet, a ``low cost access
device`` is an effort to make GNUkhata, a web-app work on a portable
android device. **gkAakash** provides students and accountants to
carry on there work while on the go. They don't have to carry a laptop
or need to have a live internet connection to work on GNUkhata. This
is a complete Android application and requires **no** internet
connection and work natively on Aakash.


About GNUKhata
--------------

GNUkhata is a Free Accounting Software which can be deployed by both
``profit making`` and ``non-profit making`` organizations. GNUkhata
provides a free and open source solution from basic book keeping to
advanced accounting. The software is developed to be flexible enough
to suit small business enterprises, personal accounting and enterprise
level needs.

- GNUkhata is a software for

  1. Maintaining books of accounts 
  2. Recording vouchers, bills, invoices and other documents
     generated in day-to-day transactions
  3. Generating reports such as Ledger Accounts, Trial Balance,
     Profit and Loss Account, Project Statement, Cash Flow
     Statement and Balance Sheet
  4. Bank Reconciliation


gkAakashCore - a backend to gkAakash
------------------------------------

`gkAakashCore <https://github.com/androportal/gkAakashCore>`_ provides
a backend to **gkAakash**. On Aakash tablet, it runs in a ``chroot``
environment(your device need to be rooted for this!). More information
can be found `here
<https://github.com/androportal/gkAakashCore/blob/master/README.rst>`_


Usage 
------

This `branch` contains an Android(4.0.3, API-15) code for the User Interface
of GNUkhata. 

User's can clone this repo by typing
::

   git clone -b ics https://github.com/androportal/gkAakash.git


if you want to clone code for Android(2.2, API-8), type
::

   git clone -b froyo https://github.com/androportal/gkAakash.git


if you want to checkout all branches then type,
::

   git checkout -b froyo remotes/origin/froyo
   git checkout -b ics remotes/origin/ics
   git checkout -b docs remotes/origin/docs
   

Importing gkAakash as an `eclipse <http://www.eclipse.org/>`_ project
---------------------------------------------------------------------
`Note`: we have used Eclipse version 3.7.2(Indigo)
 
- For setting up Android SDK and AVD, please visit this `link
  <http://developer.android.com/sdk/installing/index.html>`_
- You need to configure `ADT
  <http://developer.android.com/tools/sdk/eclipse-adt.html>`_ plugin
  on eclipse to work on Android code. Please visit `Installing ADT
  <http://developer.android.com/sdk/installing/installing-adt.html>`_
  for detail installation instructions.
  
 
After cloning the require branch, start eclipse

- go-to ``File`` menu -> ``Import``
- from the ``Import`` dialog box, select ``Android``
- from ``Android`` section, select ``Existing Android Code Into
  Workspace`` and click ``Next`` button.
- you will taken to ``Import Projects`` dialog box, click ``Browse``
  button and select the cloned repository

Important note
--------------

- to test gkAakash on tablet
- go to
  ``gkAakash/src/com/gkaakash/coreconection/CoreConnection.java`` and
  change the url from ``"http://10.0.2.2:7081"`` to
  ``"http://127.0.0.1:7081"``

Documentation
-------------

More documentation in raw `sphinx <http://sphinx.pocoo.org/>`_ format
can be found at 

::

   git clone -b docs https://github.com/androportal/gkAakash.git

please read the ``README.rst`` on how to generate html docs


License
-------

GNU `GPLv3 <http://www.gnu.org/licenses/gpl-3.0.txt>`_ 

Copyright (c) IIT Bombay, 2012.

