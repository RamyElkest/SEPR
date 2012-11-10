window.addEventListener('keypress',getInputChar,true);
window.addEventListener('keydown',getControlChar,true);
//window.onload = constructor;

function constructor()
{
	//get canvas and create a 2d context
	var getCanvas = document.getElementById("canvas");
	canvas = getCanvas.getContext("2d");
	//resize canvas to fit window
	canvas.canvas.width  = window.innerWidth;
	canvas.canvas.height = window.innerHeight;
	//create public width and height "accessors"
	canvas_width = canvas.canvas.width;
	canvas_height = canvas.canvas.height;

	IOConstructor();														//TODO: Move to a proper position
	//Start Introduction
	intro();
}


function intro()
{
	//Create HTML5 and University of York logo image instances
	HTML5_img = new Image();
	UoY_img = new Image();
	//Get the image sources
	HTML5_img.src = "http://www.w3.org/html/logo/downloads/HTML5_Logo_512.png";
	UoY_img.src = "http://www.uploadimage.co.uk/images/754782thisworks.png";
	
	state = 4;
	load_logos();
}

function load_logos()
{
	percentage = 0,
	grad_radius = 0,
	shrinking = 0;
	switch(state)
	{
		case 0:																									//start University of York logo flashing
			
			image_width = UoY_img.width;
			image_height = UoY_img.height;
			flash_logo = window.setInterval("animate_gradient(UoY_img,'blue')", 20);
			break;
		case 1:																									//start HTML5 logo flashing
			image_width = HTML5_img.width/2;
			image_height = HTML5_img.height/2;
			flash_logo = window.setInterval("animate_gradient(HTML5_img, 'rgb(160,0,0)')", 20);
			break;
		case 2:																									//Display Kouprey Start
			opacity = 0;
			fade_in_kouprey = window.setInterval(fade_kouprey, 20);
			break;
		case 3:
			setWindow = setInterval(animate_kouprey, 25);
			break;
		case 4:
			setWindow = setInterval(animate_lines, 25);
			break;
		case 5:
			BrowserText.startGame();
			break;
	}
}

function animate_gradient(image, grad_colour)  
{	
	//Clear background
	canvas.clearRect(0, 0, canvas_width, canvas_height);
	//Set gradient radius
	grad = canvas.createRadialGradient(canvas_width/2,canvas_height/2,0,canvas_width/2,canvas_height/2,grad_radius); 
	grad.addColorStop(0, grad_colour);
	grad.addColorStop(1, "black");
	// assign gradients to fill
	canvas.fillStyle = grad;
	//Draw Gradient
	canvas.fillRect(0,0, canvas_width, canvas_height);
	
	//Set Transparency
	canvas.globalAlpha = grad_radius/(canvas_height/3);
	//load the image
	canvas.drawImage(image, canvas_width/2 - image_width/2, canvas_height/2 - image_height/2, image_width, image_height);
	
	//Change radius size
	if(shrinking==0)	
		//Increment grad_radius (gradient radius)
		grad_radius += 5;
	else if(shrinking==1)
		//Decrement grad_radius (gradient radius)
		grad_radius -= 5;
		
	//Check if maximum/minimum is reaches
	if(grad_radius >= canvas_height/3)
		//Activate shrinking effect
		shrinking = 1;
	else if(grad_radius < 0) 
	{
		//move state to the next sequence
		state++;
		//reset global variables to default
		shrinking = 0;
		canvas.globalAlpha = 1;
		//Clear current logo flashing
		clearInterval(flash_logo);
		load_logos();
	}
}

function fade_kouprey()
{
	//Clear background
	canvas.clearRect(0, 0, canvas_width, canvas_height);
	//change opacity to fade in "Kouprey"
	if(opacity >= 100) canvas.globalAlpha = 1.0;
	else canvas.globalAlpha = opacity/100;

	//Displaying "Kouprey"
	var font_size = canvas_width/5;
	canvas.fillStyle="white";
	canvas.font = font_size+"px Courier New";
	canvas.fillText("KOUPREY",canvas_width/2 - (canvas.measureText("KOUPREY").width)/2, canvas_height/2); 
	
	//change opacity to fade in "The Game"
	if(opacity >= 50) canvas.globalAlpha = (opacity-50)/100;
	else canvas.globalAlpha = 0.0;
	
	//Displaying "The Game"
	font_size = font_size/3;
	canvas.fillStyle="#660000";
	canvas.font = font_size+"px Comic Sans";
	canvas.fillText("The Game",canvas_width/2 - (canvas.measureText("The Game").width)/2, canvas_height/2 + canvas.measureText("KOUPREY").width/4);
	//Displaying the bottom line
	canvas.strokeStyle="white";
	canvas.lineCap = "round";
	canvas.lineWidth = 5;
	canvas.moveTo(1 * canvas_width/10, canvas_height - canvas_height/10);
	canvas.lineTo(9 * canvas_width/10, canvas_height - canvas_height/10);
	canvas.stroke();
	
	//End the interval when opacity = 150
	if(opacity == 150)
	{
		state++;
		clearInterval(fade_in_kouprey);
		load_logos();
	}
	
	//Increment Opacity
	opacity++;
}

function interpolation(first, second, perc)
{
	var result = first - ((perc/100) * (first - second));
	return result;
	
}

function animate_kouprey()
{
	//Clear background
	canvas.clearRect(0, 0, canvas_width, canvas_height);
	//Displaying "Kouprey"
	var font_size = parseInt(interpolation(canvas_width/5, canvas_width/20, percentage));
	canvas.fillStyle="white";
	canvas.font = font_size+"px Courier New";
	canvas.fillText("KOUPREY",canvas_width/2 - (canvas.measureText("KOUPREY").width)/2, interpolation(canvas_height/2, canvas_height/15, percentage));
	//Displaying "The Game" 
	font_size = parseInt(font_size/3);
	canvas.fillStyle="#660000";
	canvas.font = font_size+"px Comic Sans";
	canvas.fillText("The Game",canvas_width/2 - (canvas.measureText("The Game").width)/2, interpolation(canvas_height/2,canvas_height/10,percentage) + canvas.measureText("KOUPREY").width/4);
	//Displaying the bottom line
	canvas.strokeStyle="white";
	canvas.lineCap = "round";
	canvas.lineWidth = 5;
	canvas.beginPath();
	canvas.moveTo(1 * canvas_width/10, interpolation((canvas_height - canvas_height/12), canvas_height/12, percentage));
	canvas.lineTo(9 * canvas_width/10, interpolation((canvas_height - canvas_height/12), canvas_height/12, percentage));
	canvas.stroke();
	
	if ( percentage == 100)
	{
		state++;
		clearInterval(setWindow);
		load_logos();
	}
	else
	{
		percentage += 1;
	}
}

function animate_lines()
{
	//Clear background
	canvas.clearRect(0, 0, canvas_width, canvas_height);
	//Displaying "Kouprey"
	var font_size = parseInt(canvas_width/20);
	canvas.fillStyle="white";
	canvas.font = font_size+"px Courier New";
	canvas.fillText("KOUPREY", canvas_width/2 - (canvas.measureText("KOUPREY").width)/2, canvas_height/15);
	//Fading "The Game" 
	canvas.globalAlpha = 1.0 - (percentage/100);
	font_size = parseInt(font_size/3);
	canvas.fillStyle="#660000";
	canvas.font = font_size+"px Comic Sans";
	canvas.fillText("The Game",canvas_width/2 - (canvas.measureText("The Game").width)/2, canvas_height/10 + canvas.measureText("KOUPREY").width/4);
	canvas.globalAlpha = 1.0;
	//Displaying the top line
	canvas.strokeStyle="white";
	canvas.lineCap = "round";
	canvas.lineWidth = 5;
	canvas.beginPath();
	canvas.moveTo(1 * canvas_width/10, canvas_height/12);
	canvas.lineTo(9 * canvas_width/10, canvas_height/12);
	canvas.stroke();
	//introducing line seperators
	//Left line
	canvas.beginPath();
	canvas.moveTo(canvas_width/3, canvas_height/12);
	canvas.lineTo(canvas_width/3, interpolation(canvas_height/12, canvas_height, percentage));
	canvas.stroke();
	//Right line
	canvas.beginPath();
	canvas.moveTo(2 * canvas_width/3, canvas_height/12);
	canvas.lineTo(2 * canvas_width/3, interpolation(canvas_height/12, canvas_height, percentage));
	canvas.stroke();
	//Input Line
	canvas.strokeStyle="#330000";
	canvas.beginPath();
	canvas.moveTo( canvas_width/3, 10 * canvas_height/12);
	canvas.lineTo(interpolation( canvas_width/3, 2 * canvas_width/3, percentage), 10 * canvas_height/12);
	canvas.stroke();
	
	if ( percentage == 100)
	{
		state++;
		clearInterval(setWindow);
		load_logos();
	}
	else
	{
		percentage += 1;
	}
}
//=========================================================================== JAVA STUFF =================================================================================================//
function IOConstructor()
{
	lines = [];
	var font_size = parseInt(canvas_height/50);
	lines.length = parseInt((10.5 * canvas_height/12) / font_size);
	
	input = "";
	prevInput = [];
	prevInput.length = 30;
	input_font_size = canvas_height/10;
	
	inputEnabled = true;
	
	return;
	
}

/**
* Right Frame
* Writing the output from the java to the bowser canvas
**/
function write(toWrite) 
{
		//Clear right frame background
		canvas.clearRect(2 * canvas_width/3 + canvas.lineWidth/2, canvas_height/11, canvas_width, canvas_height);
		var font_size = parseInt(canvas_height/50),
			output_height = canvas_height/9;
		canvas.fillStyle = "gray";
		canvas.font = font_size+"px Courier New";
		var linesToAdd = toWrite.split('\n');
		var newLines = [],
			tempLines = [];
		for (var i=0;i<linesToAdd.length;i++)
		{
			tempLines = splitLines(linesToAdd[i], canvas_width/4);									//Split lines if bigger than width
			newLines = lines.concat(tempLines);														//Push the new lines at the end of the array
			lines = newLines.slice(tempLines.length, newLines.length);								//Pop the number of lines added
		}
		for (var i=0;i<lines.length;i++)
		{
			if (!lines[i]) lines[i] = "";
			canvas.fillText(lines[i], 0.68 * canvas_width, output_height);
			output_height += font_size;
		}
		
}

/**
* Left Frame
* Writing the output from the java to the bowser canvas
**/
function writeStatus(toWrite) 
{
		//Clear right frame background
		canvas.clearRect(0, canvas_height/11, canvas_width/3, canvas_height);
		var font_size = parseInt(canvas_height/40),
			output_height = canvas_height/9,
			linesToPrint = toWrite.split('\n'),
			wrappedLines = [];
		
		//Get font size that will allow content to fit in the specified area
		while( parseInt(canvas.measureText(toWrite).width / (canvas_width/4)) * font_size > 0.8 * canvas_height)
		font_size -= 2;
		
		canvas.font = font_size+"px Calibri";
		canvas.fillStyle = "purple";
		for (var i=0;i<linesToPrint.length;i++)
		{
			wrappedLines = wrappedLines.concat(splitLines(linesToPrint[i], canvas_width/4));			//Split lines if bigger than width
		}
	
		for (var i=0;i<wrappedLines.length;i++)
		{
			canvas.fillText(wrappedLines[i], 0, output_height);
			output_height += font_size;
		}
	
		
}

/**
 *	Divide a phrase into an arra of lines to fit within a given pixel width
 **/
function splitLines(line, maxLength)
	{
		if (!line || line == "") return [""];
		var lineSplit = line.split(" "),
			lineArray = [],
			tempLine = "",
			measure = 0;
		for (var n=0;n<lineSplit.length;n++)
		{
			var word = lineSplit[n];
			measure = canvas.measureText(tempLine).width;
			if (measure > maxLength)
			{
				lineArray.push(tempLine);
				tempLine = word + " ";
			}
			else
			{
				//Additional check to see if no spaces have been entered
				if(measure > maxLength)
					{
						lineArray.push(tempLine);
						tempLine = word + " ";
					}
				tempLine += word + " ";
			}
		}
		lineArray.push(tempLine);
		
		return lineArray;
		
	}
	
function printInput(key)
	{
		//Clear input frame
		canvas.clearRect(canvas_width/3 + canvas.lineWidth/2, 10 * canvas_height/12 + canvas.lineWidth/2, canvas_width/3 - canvas.lineWidth, canvas_height);
		
		switch(key)
		{
			case 8:				//Backspace
			case 46:			//Delete
				if (input.length > 0)
					input = input.substr(0,input.length-1);
				break;
				
			case 13:			//Enter Pressed
				//Send input to game
				BrowserText.getFromBrowser(input);
				input = "";
				input_font_size = canvas_height/10;
				break;
				
			default:			//character
				input += String.fromCharCode(key);
				break;
		}
		//adjust font size
		while( parseInt(canvas.measureText(input).width / (canvas_width/4)) * input_font_size > 1/12 * canvas_height)
		input_font_size --;
		
		//Displaying input
		canvas.fillStyle="red";
		canvas.font = input_font_size + "px Calibri";
		//canvas.fillText(input,canvas_width/2 - (canvas.measureText(input).width)/2, 19 * canvas_height/20);
		
		var wrappedLines = splitLines(input, canvas_width/4);			//Split lines if bigger than width
		var input_height = 10 * canvas_height/12 + input_font_size;
		
		for (var i=0;i<wrappedLines.length;i++)
		{
			if (canvas.measureText(wrappedLines[i]) > canvas_width/4)
				{
					input_font_size --;
					canvas.font = input_font_size + "px Calibri";
				}
			canvas.fillText(wrappedLines[i], canvas_width/2 - (canvas.measureText(wrappedLines[i]).width)/2, input_height);
			input_height += input_font_size;
		}
		
	}
	
function getInputChar(event)
	{
		var charCode = (event.keyCode) ? event.keyCode : event.charCode;
		
		if (inputEnabled)
			printInput(charCode);
	}
	
function getControlChar(event)
	{
		var controlChar = (event.keyCode) ? event.keyCode : event.charCode;
		
		if (controlChar == 8 || controlChar == 46)				//Backspace and Delete Respectively
			if (inputEnabled)
				//Send null to printInput to refresh input display
				printInput(controlChar);
	}
	
function disableInput()
	{
		inputEnabled = false;
	}
function enableInput()
	{
		inputEnabled = true;
	}
//Start Java Deployment
function linkToJar()
{
	var attributes = {
					id:			"BrowserText",
					code:       "view.BrowserTextHandler",
					archive:    "Yaks.jar, lib/plugin.jar",
					width:      1,
					height:     1
				};
	var parameters = {jnlp_href:"launch.jnlp"};
	var version = "1.6";
	deployJava.runApplet(attributes, parameters, version);
	//Start Constructing Page
	constructor();
}













	
	
