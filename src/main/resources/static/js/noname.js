
url = window.location;
url = url.toString();

if(/Android|Windows Phone|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)){
	url = window.location.toString();
	if (url.match(/\/\/www\.shuquge\.com\/$/) || url.match(/\/\/www\.shuquge\.com$/)) { Go('https://m.shuquge.com/'); }

	id = url.match(/\/category\/(\d+)_(\d+)\.html/);
	if (id){Go('https://m.shuquge.com/sort/'+id[1]+'/0_'+id[2]+'.html');}

	id = url.match(/top\.html/);
	if (id){Go('https://m.shuquge.com/store/');}

	id = url.match(/full\//);
	if (id){Go('https://m.shuquge.com/quanben/1.html');}
	id = url.match(/full\/(\d+)\//);
	if (id){Go('https://m.shuquge.com/quanben/'+id[1]+'.html');}

	id = url.match(/\/txt\/(\d+)\/index\.html/);
	if (id){Go('https://m.shuquge.com/s/'+id[1]+'.html');}
	id = url.match(/\/txt\/(\d+)\/$/);
	if (id){Go('https://m.shuquge.com/s/'+id[1]+'.html');}
	id = url.match(/\/txt\/(\d+)\/(\d+)\.html/);
	if (id){Go('https://m.shuquge.com/chapter/'+id[1]+'_'+id[2]+'.html');}

}
function Go(url) {
	window.location = url;
}

function search(){
document.writeln("<div class=\"search\">");
document.writeln("	<form action=\"/search.php\" method=\"post\" onsubmit=\"if(q.value==\'\'){alert(\'提示：请输入小说名称或作者名字！\');return false;}\">");
document.writeln("	<input type=\"hidden\" name=\"s\" value=\"6445266503022880974\"><input type=\"search\" class=\"text\" name=\"searchkey\" placeholder=\"小说名称、作者\" value=\"\" />");
document.writeln("	<input type=\"submit\" class=\"btn\" value=\"搜 索\">");
document.writeln("	</form>");
document.writeln("</div>");

    if (getCookie("username")) {
        document.writeln("<div style=\"float: right;\" class=\"search\">欢迎您 " + getCookie("username") + " ，[<a href=\"/logout.php\">注销</a>]</div>");
    } else {
		var jurl = window.location.href;
		var index = jurl.lastIndexOf("jumpurl=");
		if(index > 0)
		{
			jurl = jurl.substr(index+8);
		}
        document.writeln("<div style=\"float: right;\" class=\"search\">欢迎您，[<a href='/login.php?jumpurl="+jurl+"'>登录</a>]或[<a href='/register.php?jumpurl="+jurl+"'>注册</a>]</div>");
    }

}

function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}