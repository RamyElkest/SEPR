	<style type="text/css">
	body{
		font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
	}
	#theBox{
		width:500px;
		border:1px solid #CCC;
		color: #317082;
	}
	#theBox2{
		width:400px;
		border:1px solid #CCC;
		color:red;
		text-align:center;
	}
	#theBox3{
		width:800px;
		height:50px;
		border:1px solid #CCC;
		color:red;
		text-align:center;
	}
	</style>
	<script type="text/javascript">
	/************************************************************************************************************
	(C) www.dhtmlgoodies.com, October 2005
	
	This is a script from www.dhtmlgoodies.com. You will find this and a lot of other scripts at our website.	
	
	Terms of use:
	You are free to use this script as long as the copyright message is kept intact. However, you may not
	redistribute, sell or repost it without our permission.
	
	Thank you!
	
	www.dhtmlgoodies.com
	Alf Magne Kalleland
	
	************************************************************************************************************/	
	var fitTextInBox_maxWidth = false;
	var fitTextInBox_maxHeight = false;
	var fitTextInBox_currentWidth = false;
	var fitTextInBox_currentBox = false;
	var fitTextInBox_currentTextObj = false;
	function fitTextInBox(boxID,maxHeight)
	{
		if(maxHeight)fitTextInBox_maxHeight=maxHeight; else fitTextInBox_maxHeight = 10000;
		var obj = document.getElementById(boxID);
		fitTextInBox_maxWidth = obj.offsetWidth;	
		fitTextInBox_currentBox = obj;
		fitTextInBox_currentTextObj = obj.getElementsByTagName('SPAN')[0];
		fitTextInBox_currentTextObj.style.fontSize = '1px';
		fitTextInBox_currentWidth = fitTextInBox_currentTextObj.offsetWidth;
		fitTextInBoxAutoFit();
		
	}	
	
	function fitTextInBoxAutoFit()
	{
		var tmpFontSize = fitTextInBox_currentTextObj.style.fontSize.replace('px','')/1;
		fitTextInBox_currentTextObj.style.fontSize = tmpFontSize + 1 + 'px';
		var tmpWidth = fitTextInBox_currentTextObj.offsetWidth;
		var tmpHeight = fitTextInBox_currentTextObj.offsetHeight;
		if(tmpWidth>=fitTextInBox_currentWidth && tmpWidth<fitTextInBox_maxWidth && tmpHeight<fitTextInBox_maxHeight && tmpFontSize<300){		
			fitTextInBox_currentWidth = fitTextInBox_currentTextObj.offsetWidth;	
			fitTextInBoxAutoFit();
		}else{
			fitTextInBox_currentTextObj.style.fontSize = fitTextInBox_currentTextObj.style.fontSize.replace('px','')/1 - 1 + 'px';
		}		
	}	
	</script>
