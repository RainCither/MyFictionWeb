function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+365)
    document.cookie=c_name+ "=" +escape(value)+";expires="+exdate.toGMTString()+";path=/";
}

function getCookie(c_name)
{
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1){ 
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) c_end=document.cookie.length;
            return unescape(document.cookie.substring(c_start,c_end));
        } 
    }
    return "";
}

function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    document.cookie= name + "=;expires="+exp.toGMTString();
}


( function() {
	var ua = navigator.userAgent.toLowerCase();
	var is = (ua.match(/\b(chrome|opera|safari|msie|firefox)\b/) || [ '',
			'mozilla' ])[1];
	var r = '(?:' + is + '|version)[\\/: ]([\\d.]+)';
	var v = (ua.match(new RegExp(r)) || [])[1];
	jQuery.browser.is = is;
	jQuery.browser.ver = v;
	jQuery.browser[is] = true;

})();

var speed = 5;
var autopage;// = $.cookie("autopage");
var night;
var timer;
var temPos=1;

$(document).ready(function(){
	if(typeof(next_page) != "undefined" ) {
		next_page = next_page;
		autopage = getCookie("autopage");
		
		sbgcolor = getCookie("bcolor");
		setBGColor(sbgcolor);
		
		font = getCookie("font");
		setFont(font);
		
		size = getCookie("size");
		setSize(size);
		
		fontcolor = getCookie("fontcolor");
		setColor(fontcolor);
		
		width = getCookie("width");
		setWidth(width);
		
		speed = getCookie("scrollspeed");
		
		if(autopage==1) {
			$('#autopage').attr("checked",true);
			speed = getCookie("scrollspeed");
			scrollwindow();
		}
		
		night = getCookie('night');
		if(night==1) {
			$('#night').attr('checked',true);
			setNight();
		}
		document.onmousedown=sc;
		document.ondblclick=scrollwindow;
	}
});

if (getCookie("bgcolor") != '') {
    wrapper.style.background = getCookie("bgcolor");
    document.getElementById("bcolor").value = getCookie("bgcolor")
}

function changebgcolor(id) {
    wrapper.style.background = id.options[id.selectedIndex].value;
    setCookie("bgcolor", id.options[id.selectedIndex].value, 365)
}

function setBGColor(sbgcolor){
	$('#wrapper').css("backgroundColor",sbgcolor);
	setCookie("bcolor",sbgcolor,365);
}

function setColor(fontcolor) {
	$("#content").css("color",fontcolor);
	setCookie("fontcolor",fontcolor,365);
}

function setSize(size) {
	$("#content").css("fontSize",size);
	setCookie("size",size,365);
}
function setFont(font) {
	$("#content").css("fontFamily",font);
	setCookie("font",font,365);
}
function setWidth(width){
	$('#content').css("width",width);
	setCookie("width",width,365);
}
function setNight(){
	if($('#night').is(':checked')==true) {
		$('body,div,.this').css("backgroundColor","#111111");
		$('div,a').css("color","#999999");
		setCookie("night",1,{path:'/',expires:365});
	} else {
		$('body,div,.this').css("backgroundColor","");
		$('div,a').css("color","");
		setCookie("night",0,{path:'/',expires:365});
	}
}

function scrolling() 
{  
	var currentpos=1;
	if($.browser.is=="chrome" |document.compatMode=="BackCompat" ){
		currentpos=document.body.scrollTop;
	}else{
		currentpos=document.documentElement.scrollTop;
	}

	window.scroll(0,++currentpos);
	if($.browser.is=="chrome" || document.compatMode=="BackCompat" ){
		temPos=document.body.scrollTop;
	}else{
		temPos=document.documentElement.scrollTop;
	}

	if(currentpos!=temPos){
        ///msie/.test( userAgent )
        var autopage = getCookie("autopage");
        if(autopage==1&&/next_page/.test( document.referrer ) == false) location.href=next_page;
		sc();
	}
}

function scrollwindow(){
	timer=setInterval("scrolling()",250/speed);
}

function sc(){ 
	clearInterval(timer); 
}

function setSpeed(ispeed){ 
	if(ispeed==0)ispeed=5;
	speed=ispeed;
	setCookie("scrollspeed",ispeed,365);
}

function setAutopage(){
	if($('#autopage').is(":checked") == true){
		$('#autopage').attr("checked",true);	
		setCookie("autopage",1,365);
	}else{
		$('#autopage').attr("checked",false);
		setCookie("autopage",0,365);
	}
}

var timestamp = Math.ceil((new Date()).valueOf()/1000); //???????????????
var flag_overtime = -1;

		
function textselect(){
document.writeln("<div id=\"page_set\">");
document.writeln("<select onchange=\"javascript:setFont(this.options[this.selectedIndex].value);\" id=\"bcolor\" name=\"bcolor\"><option value=\"??????\">??????</option><option value=\"??????????????????\">??????</option><option value=\"??????\">??????</option><option value=\"??????_GB2312\">??????</option><option value=\"????????????\">??????</option><option value=\"??????????????????\">??????</option><option value=\"??????\">??????</option></select>");
document.writeln("<select onchange=\"javascript:setColor(this.options[this.selectedIndex].value);\" id=\"bcolor\" name=\"bcolor\"><option value=\"#000\">??????</option><option value=\"#000\">??????</option><option value=\"#9370DB\">??????</option><option value=\"#2E8B57\">??????</option><option value=\"#2F4F4F\">??????</option><option value=\"#778899\">??????</option><option value=\"#800000\">??????</option><option value=\"#6A5ACD\">??????</option><option value=\"#BC8F8F\">??????</option><option value=\"#F4A460\">??????</option><option value=\"#F5F5DC\">??????</option><option value=\"#F5F5F5\">??????</option></select>");
document.writeln("<select onchange=\"javascript:setSize(this.options[this.selectedIndex].value);\" id=\"bcolor\" name=\"bcolor\"><option value=\"#E9FAFF\">??????</option><option value=\"19pt\">??????</option><option value=\"10pt\">10pt</option><option value=\"12pt\">12pt</option><option value=\"14pt\">14pt</option><option value=\"16pt\">16pt</option><option value=\"18pt\">18pt</option><option value=\"20pt\">20pt</option><option value=\"22pt\">22pt</option><option value=\"25pt\">25pt</option><option value=\"30pt\">30pt</option></select>");
document.write('<select name=scrollspeed id=scrollspeed  onchange="javascript:setSpeed(this.options[this.selectedIndex].value);" ><option value=5>??????</option><option value=1>??????</option><option value=2>???2</option><option value=3>???3</option><option value=4>???4</option><option value=5>???5</option><option value=6>???6</option><option value=7>???7</option><option value=8>???8</option><option value=9>???9</option><option value=10>??????</option></select>');
document.writeln("<select onchange=\"javascript:setBGColor(this.options[this.selectedIndex].value);\" id=\"bcolor\" name=\"bcolor\"><option value=\"#E9FAFF\" style=\"background-color: #E9FAFF;\">??????</option><option value=\"#E9FAFF\" style=\"background-color: #E9FAFF;\">??????</option><option value=\"#FFFFFF\" style=\"background-color: #FFFFFF;\">??????</option><option value=\"#000000\" style=\"background-color: #000000;color:#FFFFFF;\">??????</option><option value=\"#FFFFED\" style=\"background-color: #FFFFED;\">??????</option><option value=\"#EEFAEE\" style=\"background-color: #EEFAEE;\">??????</option><option value=\"#CCE8CF\" style=\"background-color: #CCE8CF;\">??????</option><option value=\"#FCEFFF\" style=\"background-color: #FCEFFF;\">??????</option><option value=\"#EFEFEF\" style=\"background-color: #EFEFEF;\">??????</option><option value=\"#F5F5DC\" style=\"background-color: #F5F5DC;\">??????</option><option value=\"#D2B48C\" style=\"background-color: #D2B48C;\">??????</option><option value=\"#C0C0C0\" style=\"background-color: #E7F4FE;\">??????</option></select>");
document.writeln("<select onchange=\"javascript:setWidth(this.options[this.selectedIndex].value);\" id=\"bcolor\" name=\"bcolor\"><option value=\"95%\">??????</option><option value=\"95%\">??????</option><option value=\"85%\">85%</option><option value=\"76%\">75%</option><option value=\"67%\">65%</option><option value=\"53%\">50%</option><option value=\"41%\">40%</option></select>");
document.writeln("</select>??????<input type=checkbox name=autopage id=autopage onchange=\"javascript:setAutopage();\" value=\"\" />&nbsp;??????<input type=checkbox name=night id=night onchange=\"javascript:setNight();\" value=\"\" /></div>");
}


