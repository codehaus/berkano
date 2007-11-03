Get jta at http://java.sun.com/products/jta/ and put the zipfile provided by sun in the lib directory of this project.
If the file you get wasn't called jta-1_0_1B-classes.zip, modify the project.properties to correct the jar override.
You'll also need to renamed to .jar so that tomcat picks it up from the WEB-INF/lib directory.
