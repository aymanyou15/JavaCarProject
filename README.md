# JavaCarProject


RC Car controlled by Java desktop Application



Supervised By: Eng. Eman Hesham
Contributors
Marwa Mustafa
Ayman Youssef
Ahmed M. Soliman
Mohamed Abdel Fatah
Ahmed Yehia
Table of content
Abstract…………………………………………………..3
Software implementation……………………………….3
2.1. Gauge……………………………………………….4
2.2. Slider………………………………………………...4
2.3. Start Button…………………………………………5
2.4. ComboBox………………………………………….5
2.5. Photos (Image View)...........................................5
2.6. Event Handlers…………………………………….6
2.6.1. Slider mouse drag handler………………..6
2.6.2. Anchor pane Key pressed handler……….7
2.6.3. Anchor pane Key release handler………..7
2.6.4. Start Button pressed handler……………..7
2.7. Socket programming………………………………8
Hardware implementation……………………………...9
		3.1. Physical components...………………………9
		3.2. Connections………………………………….10
		3.3. Arduino code…………………………………11
Communication………………………………………...14
	4.2. Serial Communications……………………..15
	4.2. End-User-Interface………………………….15


	

Abstract
The main idea of the project is to control an arduino based remote controller Car using a desktop Java application that uses Javafx as it is gui framework, the connection between the project main two components (the hardware and software) is done through Bluetooth that works on top of serial communication protocol or by connecting the arduino directly to any port of the lab ports.


Software Implementation
The graphical user interface (gui) was built using javafx, a graphical toolkit that provides a set of functionalities in order to make gui development easier that before (in applet and swing)
We can classify the gui built on this project into 3 main parts:
Gauge to create the speedometer object
Slider to control the speed of the car
Keyboard event listener to control the motion of the car
We will go in more details of each part along with other components used



2.1. Gauge
It is a library that attempts to fill the gap in the javaFx list of features, the library was made by a german software engineer called Gerrit Granwald (Hansolo).
Gerrit created the Medusa library in 2015, it is a great library that includes many gauges like speedometers and watches and many other gauges.

2.2. Slider
It is a library offered by JavaFx to create a slider and use it to control whatever values you want to control.
We are using a horizontal slider to control the speed of the car.






2.3. Start Button
JavaFx offers a great Button library to create a button and set its shape and event handlers.






2.4. ComboBox
Great library to create a combobox to store our the system ports so the user can choose the port that is connected to arduino.



2.5. Photos (Image View)
The project design contains 7 images to make the final form more friendly to users. 






2.6. Event Handlers

2.6.1. Slider mouse drag handler
When the slider value is changed due to dragging the mouse over it, the speedometer value and RPM gauge values are changed also.


















2.6.2. Anchor pane Key pressed handler
When any of the control keys are pressed a value is sent to the arduino, and according to this value the car will take an action. 
For further information about the communication algorithm check connections section

2.6.3. Anchor pane Key release handler
When any of the control keys are released a value is sent to the arduino, so the car will stop. 
We want to move the car only when the control keys are pressed.

		2.6.4. Start button pressed handler
When the button is pressed the application will connect to the arduino so users can control the car.
If there is no chosen port or there is a problem in the connection, an alerting message will pop-up to inform the user of what he should do.
If the application connects successfully to the MCU the button color will be red and its text will be “End”.
If the user clicks on it again, the connection will terminate and “Start” shows up again.


2.7. Socket Programming
As an extra feature, Socket programming was used to create a server that communicate with all connected cars through their main application (what we can call clients on this context) and plot their paths
Program flow:

The server starts to run before any other client.
Then it starts a blocking state (on a seperate thread) waiting for connection with a client.
When a client is started the connection is established between the server and the client.
Every time the client application sends an order to the car it also sends the same order to the server so that it tracks the orders given to the car and maps it’s path.


Hardware Implementation
Moving on to the hardware implementation the project used arduino as the microcontroller the controls the car,
The microcontroller receives orders from the desktop application through the communication channel (will be covered in the next section) and processes those orders correspondingly.
The main components of the hardware side is:
The physical components (arduino - car body - motor driver - adopter)
Connections
Arduino code

3.1. Physical components
The components used on the project can be listed as follows

Component name
Purpose
Arduino uno
The controlling brain
Motor driver (H-Bridge)
To control the car motors
HC-05 Bluetooth Module
To achieve wireless comm.










3.2. Connections








3.3. Arduino code




Communications
The Arduino board MCU is ATmega328p so the communication protocol registers are 8-bits, that means we are only limited to use a range from 0 to 255 to send data from the PC to the arduino.
We have 7 keyboard keys to control the car (W - S - D - A - O - L - K), data is transmitted when only 5 of them are pressed (W - S - D - A) and K.
When K is pressed 100 is sent to the arduino and it is translated there to switch the buzzer on, when K is pressed again 120 is sent and it is translated to switch the buzzer off.
we need to distinguish between the direction controllers (W - S - D - A) so there are 4 ranges, each range refers to one direction and the speed is embedded into that direction.
The slider value is divided by 12.75 and added to the start point of each direction.
Forward (W): the range is (from 0 to 20), one value is sent according to the speed or the slider value.
Downward (S): the range is (from 22 to 42), one value is sent according to the speed or the slider value.
Left (A): the range is (from 44 to 64), one value is sent according to the speed or the slider value.
Right (D): the range is (from 66 to 86), one value is sent according to the speed or the slider value.
To stop the car in the Key released handler 205 is sent and that value is translated on the arduino side to stop the car.

	4.1. Serial Communications
Serial Communications were integral to the project, to send commands to the Arduino to control the car wirelessly to guarantee the freedom of the car movement, the communications were done by connecting to a serial computer port, and connecting the bluetooth of the computer to the HC - 05 bluetooth module, that communicates with the Arduino through UART.

	4.2. End-User-Interface
The user interface is streamlined efficiently enough that the user simply chooses the COM port the Arduino is connected upon, and press on the button start, by these few steps the connection between the computer and the car is already established, and the car is waiting for the user’s commands, the commands are sent to the Arduino when the user presses on the keys that control direction or the speed of the car, the user can check which key does what function in the user guide.
