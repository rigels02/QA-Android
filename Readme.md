# Android QA  application

Android "Questions and Answers" knowledge base application.
It can receive knowledge-base file from Restful web server running on
JavaFX application qa-javaFX-module, github:

https://github.com/rigels02/goods-qa-main/tree/master/qa-javaFX-module

The application use jar libraries from QA-Main project
what is available as goods-qa-main on Github:

https://github.com/rigels02/goods-qa-main

The following modules (jar libs) are used:

- QA-module
  https://github.com/rigels02/goods-qa-main/tree/master/qa-module
  
- SimpleParserInterfaceImpl module
  https://github.com/rigels02/goods-qa-main/tree/master/XmlParsers/SimpleParserInterfaceImpl  

- Interfaces module
  https://github.com/rigels02/goods-qa-main/tree/master/XmlParsers/Interfaces

These modules must be installed in local Maven repository on local PC before building of 
Android application.

  1.The easiest way is to install all as one from console:
~~~~
  git clone https://github.com/rigels02/goods-qa-main.git
  cd goods-qa-main
  mvn install -DskipTests
~~~~

  2.Second way is to install only necessary modules:
~~~~
   git clone https://github.com/rigels02/goods-qa-main.git
   cd goods-qa-main
   cd XmlParsers\SimpleParserInterfaceImpl
   mvn install -DskipTests
   cd ../Interfaces
   mvn install -DskipTests
   cd ../qa-module
   mvn install -DskipTests
~~~~
 
The following modules (jar libs) should be included as dependencies in app module build.gradle file:
  
~~~~
implementation 'com.atlassian.commonmark:commonmark:0.10.0'
    implementation('org.simpleframework:simple-xml:2.7.1') {
        exclude group: 'xpp3', module: 'xpp3' //comflict with android xpp3 impl
        exclude group: 'stax', module: 'stax-api' //conflict with android
    }
    implementation  group: 'org.rb.mm', name: 'SimpleParserInterfaceImpl', version: '1.0-SNAPSHOT'
    implementation  group: 'org.rb', name: 'qa-module', version: '1.0-SNAPSHOT'
   
~~~~  

## Restful client as Retrofit
 To be added...