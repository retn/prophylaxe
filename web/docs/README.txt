README
======

This directory should be used to place project specfic documentation including
but not limited to project notes, generated API/phpdoc documentation, or
manual files generated or hand written.  Ideally, this directory would remain
in your development environment only and should not be deployed with your
application to it's final production location.


Setting Up Your VHOST
=====================

The following is a sample VHOST you might want to consider for your project.

<VirtualHost *:80>
   DocumentRoot "C:/xampp/htdocs/spl/public"
   ServerName .local

   # This should be omitted in the production environment
   SetEnv APPLICATION_ENV development

   <Directory "C:/xampp/htdocs/spl/public">
       Options Indexes MultiViews FollowSymLinks
       AllowOverride All
       Order allow,deny
       Allow from all
   </Directory>

</VirtualHost>

Mein Vhost (Dominic Lehner):

Datei: C:\xampp\apache\conf\extra\httpd-vhosts.conf

<VirtualHost 127.0.0.1:8082>
	ServerName spl.local
	DocumentRoot "C:/xampp/htdocs/spl/public"
 
	SetEnv APPLICATION_ENV "development"
 
	<Directory "C:/xampp/htdocs/spl/public">
		DirectoryIndex index.php
		AllowOverride All
		Order allow,deny
		Allow from all
	</Directory>
</VirtualHost>


Datei: C:\xampp\apache\conf\httpd.conf

#spl
Listen 127.0.0.1:8082


Datenbankeinstellungen:
Datei application/configs/application.ini
