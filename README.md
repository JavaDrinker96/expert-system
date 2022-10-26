# Expert system
A classic expert system for any of the topics. With this project, you can easily train an expert system (create a knowledge base), 
save it to an excel file and use it.

## How to use it?
There are two modes of operation, training and expertise.  To switch between, use the two tabs at the top.<br/>
![image](https://user-images.githubusercontent.com/90173654/197949572-d831c19c-9626-467e-99af-9fc66b6770e2.png)

### Studying mode
In the "Folder for file" field, specify the path to the folder where the knowledge base will be saved (you can use the "Choose folder" button, 
nter the file name in the "File name" field.

In the first list, enter the names of the objects of your system, in the second - the options that may belong to each object.

To start learning, click the "Start studying" button.

It will look something like this:
![image](https://user-images.githubusercontent.com/90173654/197948186-477785bf-b9cc-48de-87a5-f6105b10a26d.png)
After that, the system will start training, asking if a particular object has a certain option.<br/>
![image](https://user-images.githubusercontent.com/90173654/197948457-28175899-09d9-4a4f-ab0f-99e007ac59e6.png)

After all, you will get a knowledge base in an excel file.<br/>
![image](https://user-images.githubusercontent.com/90173654/197948737-d50121c6-3076-4fbe-a417-9498b143f79e.png)

### Expertise mode

![image](https://user-images.githubusercontent.com/90173654/197948783-c75b8912-f7ea-4057-afbb-e3a0ae2cb9b4.png)

In the "Path to knowledge base" field, enter the path to the excel file where your excel file is stored. The "Choose file" 
button will make the choice more convenient.

After that, click the "Run ES" button to start the system examination.

The system will find out from you whether this or that object has a certain option, and then it will output an answer.

![image](https://user-images.githubusercontent.com/90173654/197949432-a0bbfc8c-46ec-4f45-990f-ada5d515c2e1.png)
![image](https://user-images.githubusercontent.com/90173654/197949458-fc02034b-0d40-4be2-a8a0-7c401a7f4286.png)

## The main technology stack
- Java 8
- Spring boot 2.2.1
- Java FX
- Java Fx weaver 1.3.0
- Apache POI 3.17
