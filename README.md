# AWSCertificationTool
A Tool That I developed to parse PDF files from AWS Courses and generate a question view using Scala FX


This project was developed in Intelli J IDE. I Would recommend using the same IDE for simplicity. One thing to note here that 
this project requires Java 11. I developed using OpenJDK however, I would recommend trying Oracle Java 11 and map the project settings to Oracle.
Reason being: Oracle Java 11 supports JavaFX, while Open JDK may require you to install JavaFX.


For Windows:
https://www.oracle.com/java/technologies/downloads/#java11-windows

For Mac:
https://www.oracle.com/java/technologies/downloads/#jdk20-mac

For Linux:
https://www.oracle.com/java/technologies/downloads/#jdk20-linux


One of the challenges in developing this tool is to parse PDF from AWS Courses. Some of the PDF books that I used for an example:


[Ben-Piper_-David-Clinton-Aws-Certified-Cloud-Practitioner-Study-Guide_-Clf-C01-Exam-Sybex-2019.pdf](https://github.com/RafatKhandaker/AWSCertificationTool/files/11214383/Ben-Piper_-David-Clinton-Aws-Certified-Cloud-Practitioner-Study-Guide_-Clf-C01-Exam-Sybex-2019.pdf)


This application basically, structures the courses from the folder directory of the input files.
If you create a new folder for the input directory, using the same existing structure. This application will automatically populate the dropdown list.


Currently, the project will build and run from IntelliJ IDEA. 


Intelli J:

https://www.jetbrains.com/idea/download/#section=windows


You will need to install the Scala & SBT plugins to run the project in Intelli J.  

Currently, Eclipse is not well supported for general scala projects. I am not sure if that has changed from the time I uploaded this project.

In the future I will update a link to the executable Jar file. So users may simply install Java 11 and run the jar executable file.


Explanation of the challenges of development and how to use this tool effectively

https://youtu.be/cp0x8BOdW2o   




UX User Interface Design (Simple and Easy):

Initial Scene:

![AWSToolInitial](https://user-images.githubusercontent.com/19369242/231552297-52d46afe-5e14-43cc-9bdb-30e041fa8ac6.PNG)

Transitions:

![AWSQuestionTool](https://user-images.githubusercontent.com/19369242/231552064-6efd54d2-6e1d-4844-ba04-5ca21337fa97.PNG)
