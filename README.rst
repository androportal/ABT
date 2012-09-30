=========================
GNUkhata on Aakash tablet
=========================

Porting of `GNUkhata <http://gnukhata.org/>`_ on `Aakash
<http://www.iitb.ac.in/AK/Aakash.htm>`_ tablet, a ``low cost access
device`` is an effort to make GNUkhata, a web-app work on a portable
android device. **gkAakash** provides accountants to carry on there
work while on the go. They no need to carry a laptop or a live
internet connection to work on GNUkhata. This is a complete Android
application requires **no** internet connection and work natively on
Aakash.


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


Usage
-----

This `branch` contains an Android(2.2, API-8) code for the User Interface
of GNUkhata. 

User's can clone this repo by typing
::

   git clone -b froyo https://github.com/androportal/gkAakash.git


if you want to clone code for Android(4.0.3, API-15), type
::

   git clone -b ics https://github.com/androportal/gkAakash.git


The default branch is **froyo**, which can be cloned by typing
::

   git clone https://github.com/androportal/gkAakash.git


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

