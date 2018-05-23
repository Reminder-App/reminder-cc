# Reminder App
This project integrated the *Positivo Challenge*, a research and development project carried out by the *University of Brasília* (UnB) in partnership with *Positivo*.

### Features
The features present in this release are:

![Feature Model](https://i.imgur.com/ENMbZFT.png)

# Steps to Generate a Product
For the project to work, you must install and configure the  [Hepheatus](https://github.com/hephaestus-pl/hephaestus-base) and then follow the steps below:

1. In the directory for this project, open the **spl-config** folder. Then open the file **project.properties** and replace the ```<directory-where-this-repository-is-cloned>``` tag as requested. <br />
1.1. Then change the value of *instance-model* according to the product you want to generate. All products are on *products* folder.

2. Open the workspace where the Hepheastus tool is installed. Then go to **hephaestus-sb/bin** and run the *hepheastus* file.

3. Enter the command *start*. Then enter the **absolute path** where the *project.properties* file is located and then confirm;

4. Navigate to the target directory that was entered in the *projects.properties* file.

5. Open the terminal and enter the command below. Remember to replace ```<hephaestus-workspace-path>``` with its corresponding value:
>  *java -jar &lt;hephaestus-workspace-path&gt;/hephaestus-pp/bin/antenna-pp.jar files.pp build.lst --drop-lines*

6. After, the generated product is fully configured. So import it into the Eclipse IDE or Android Studio and emulate the Reminder App.

That is all.

Leomar Camargo, [leomarcamargodesouza@gmail.com](mailto:leomarcamargodesouza@gmail.com).
