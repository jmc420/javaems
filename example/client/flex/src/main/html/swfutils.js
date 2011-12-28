/*******************************************************************/
/*
	File: swfutils.js

	contains resize code
*/
/*******************************************************************/
/* changeBrowserTitle */

function changeBrowserTitle(title) 
{
	 document.title = title; 
	 
} // changeBrowserTitle

/*******************************************************************/
/* createFullBrowserFlash */

function createFullBrowserFlash(container) 
{
	swfobject.createCSS("html", "height:100%;");
	swfobject.createCSS("body", "height:100%;");
	swfobject.createCSS("#"+container, "margin:0; width:100%; height:100%; min-width:770px; min-height:390px;");

	window.onresize = function() 
	{
	var el = document.getElementById(container);
	var size = getViewportSize(); 

		el.style.width = size[0] < 770 ? "770px" : "100%";
		el.style.height = size[1] < 390 ? "390px" : "100%";
	};

	window.onresize();

} // createFullBrowserFlash

/*******************************************************************/
/* getViewportSize */

function getViewportSize() 
{ 
var size = [0, 0]; 

	if (typeof window.innerWidth != "undefined") 
	{ 
		size = [window.innerWidth, window.innerHeight];
	} 
	else 
	{
		if (typeof document.documentElement != "undefined" && 
		    typeof document.documentElement.clientWidth != "undefined" && 
		    document.documentElement.clientWidth != 0) 
		{
			size = [document.documentElement.clientWidth, document.documentElement.clientHeight]; 
		}
		else 
		{
			size = [document.getElementsByTagName("body")[0].clientWidth, document.getElementsByTagName("body")[0].clientHeight]; 
		}
	}

	return size; 

} // getViewportSize

/*******************************************************************/

