var isPlaying = false;
var imageStatic = "images/Starman.png";
var imageAnimated = "images/Starman.gif";
var audioFile = "audio/starman.mid";
var audioLength = 43; //Seconds

function starpower() {
	if (isPlaying) {
		stop();
	}
	else {
		start();
	}
}

function stop () {
	//reset div to static image, no audio
	var stretch;
	if (window.innerHeight > window.innerWidth)
	{
		stretch = "width='100%'";
	}
	else
	{
		stretch = "height='100%'";
	}
	document.getElementById('starman').innerHTML = "<img " + stretch + " src='" + imageStatic + "'>";
	stopSound();
	isPlaying = false;
}

function start() {
	// set div to gif and audio
	var stretch;
	if (window.innerHeight > window.innerWidth)
	{
		stretch = "width='100%'";
	}
	else
	{
		stretch = "height='100%'";
	}
	document.getElementById('starman').innerHTML = "<img " + stretch + " src='" + imageAnimated + "'>";
	playSound ("audio/starman.mid");
	setTimeout("stop()",audioLength * 1000)
	isPlaying = true;
}

function init() {
	stop();
}




function playSound(audioURL) {
 if (document.all) document.all['BGSOUND_ID'].src=audioURL;
 else self.iplayer.location.replace('jsplayer.htm?'+audioURL);
}

function stopSound() {
 if (document.all) document.all['BGSOUND_ID'].src='';
 else self.iplayer.location.replace('jsplayer.htm?stop');
}
