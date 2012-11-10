window.addEventListener('keypress',getInputChar,true);
window.addEventListener('keydown',getControlChar,true);
window.addEventListener('resize',resize,true);


function resize()
{
	//resize canvas to fit window
	if (canvas.canvas.width) 
	{
		canvas.canvas.width = window.innerWidth;
		canvas_width = canvas.canvas.width;
	}
	if (canvas.canvas.height)
	{
		canvas.canvas.height = window.innerHeight;
		canvas_height = canvas.canvas.height;
	}
	
	$("section#wrapper").height($(window).height());
	$("section#wrapper").width($(window).width());
	
	//Add adjust font size to all spans
}
function constructor()
{
	//check canvas support
	if(!window.CanvasRenderingContext2D)
	{	
		//route to CSS
		$("canvas").empty();
		state = 2;
		load_logos();
		return;
	}
	//get canvas and create a 2d context
	var getCanvas = document.getElementById("canvas");
	canvas = getCanvas.getContext("2d");
	//resize canvas to fit window
	canvas.canvas.width  = $(window).height();
	canvas.canvas.height = $(window).width();
	//create public width and height "accessors"
	canvas_width = canvas.canvas.width;
	canvas_height = canvas.canvas.height;

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
	
	state = 0;
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
		case 1:																						//start HTML5 logo flashing
			image_width = HTML5_img.width/2;
			image_height = HTML5_img.height/2;
			flash_logo = window.setInterval("animate_gradient(HTML5_img, 'rgb(160,0,0)')", 20);
			break;
		case 2:
			$("body").prepend("	<section id='wrapper'>\n\n\n		<div id='Status'><span id='StatusSpan'></span></div>\n		<section id='mainInput'>\n			<div id='Main'><span id='MainSpan'></span></div>\n			<div id='Input'><span id='InputSpan'></span></div>\n		</section>\n\n		<div id='Log'><span id='LogSpan'></span></div>\n	</section>\n\n");
			$("head").append('<link rel="stylesheet" href="koupreycss.css" type="text/css"/>');
			$("canvas").remove();
			$("section#wrapper").height($(window).height());
			$("section#wrapper").width($(window).width());
			$("body").css('background-image','url(back/mainBack2.jpg)');
			state++;
			load_logos();
			break;
		case 3:		
			IOConstructor();		
			startAttempted = true;
			window.setTimeout("BrowserText.startGame()",5000);
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
	lines.length = parseInt($('div#Log').height() / 30);
	
	input = "";
	prevInput = [];
	prevInput.length = 30;
	
	inputEnabled = true;
	
	return;
	
}

/**
* Main Frame
* Writing the output from the java to the browser canvas
**/
function write(toWrite)
{
		var linesToWrite = toWrite.split('\n');
		$('span#MainSpan').empty();
		for(var i=0;i<linesToWrite.length;i++)
		{
			$('span#MainSpan').append(linesToWrite[i]+"<br>");
		}
		log(toWrite);
		
		adjustFontSize('Main');
}

/**
* Right Frame
* Logging the output from the main div to the right div
**/
function log(toLog) 
{
		var linesToAdd = toLog.split('\n'),
			newLines = [],
			additionalLines = 0,
			charsEstimate = $('div#Log').width()/3;
			
		newLines = lines.concat(linesToAdd);									//Push the new lines at the end of the array		
		lines = newLines.slice(linesToAdd.length, newLines.length);								//Pop the number of lines added
		
		for(var i=0;i<lines.length;i++)
		{
			if(lines[i])
			additionalLines += Math.floor(lines[i].length / charsEstimate);
		}
		
		newLines = newLines.slice(linesToAdd.length + additionalLines, newLines.length);

		$('span#LogSpan').empty();
		for(var i=0;i<newLines.length;i++)
		{
			if(newLines[i]==null) newLines[i]="";
			$('span#LogSpan').append(newLines[i]+"<br>");
		}
		adjustFontSize('Log');
}

/**
* Left Frame
* Writing the output from the java to the bowser canvas
**/
function writeStatus(newStatus) 
{		
		var linesToState = newStatus.split('\n');
		$('span#StatusSpan').empty();
		for(var i=0;i<linesToState.length;i++)
		{
			$('span#StatusSpan').append(linesToState[i]+"<br>");
		}
		adjustFontSize('Status');
}

function adjustFontSize(divName)
{

	var	div = document.getElementById(divName),
		span = document.getElementById(divName+"Span"),
		divHeight = div.offsetHeight,
		fontSize = 50;
		
	span.style.fontSize = '50px';
	
	var spanHeight = span.offsetHeight;
		
	
	while(spanHeight > divHeight)
	{
		if (fontSize <= 1) {span.style.fontSize = '1px'; return;}
		else	fontSize -= 2;
		span.style.fontSize = fontSize + 'px';
		spanHeight = span.offsetHeight;
	}
}
	
function printInput(key)
	{
		switch(key)
		{
			case 8:				//Backspace
			case 46:			//Delete
				if (input.length > 0)
					input = input.substr(0,input.length-1);
				break;
				
			case 13:				//Enter Pressed
									//Send input to game
				var temp = input;	//use temp to avoid UI freeze of game froze
				input = "";
				BrowserText.getFromBrowser(temp);
				break;
				
			default:			//character
				input += String.fromCharCode(key);
				break;
		}
		$("span#InputSpan").html(input);
		adjustFontSize("Input");
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
function attemptGameStart()
	{
		if(startAttempted)
		{
			BrowserText.startGame();
		}
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













	
	
