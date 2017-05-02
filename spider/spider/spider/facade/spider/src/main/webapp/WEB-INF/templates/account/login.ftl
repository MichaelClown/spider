<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>html</title>
    <link href="http://at.alicdn.com/t/font_1459473269_4751618.css" type="text/css" rel="stylesheet"/>
    <@css href="css/bootstrap.min.css" />
    <@css href="css/style.css" />
    <@js src="js/jquery.min.js,js/bootstrap.min.js,js/snap.svg-min.js,js/html5.js" />
    <!--必要样式-->
    <@css href="css/menu_elastic.css" />
    <!--[if IE]>
    <@js src="js/html5.js" />
    <![endif]-->
</head>
<body class="huibg">
<nav class="navbar text-center">
    <button class="topleft" onclick ="javascript:history.go(-1);"><span class="iconfont icon-fanhui"></span></button>
    <a class="navbar-tit center-block">账号登陆</a>
</nav>
<div class="login">
    <div class="container logdv">
        <form method="post" action="/account/login">
            <input hidden="hidden" name="redirectUrl" id="redirectUrl" value="${(redirectUrl)!}">
            <div class="lgdv">
                <input type="text" name="cellPhone" id="cellPhone" class="boxt" placeholder="手机号码">
            </div>
            <div class="lgdv">
                <div class="yzm">获取验证码</div>
                <input type="text" name="identity" id="identity" class="boxt" placeholder="输入验证码">
            </div>
            <input type="submit" class="btnlg" value="登 陆" />
        </form>
    </div>
</div>

<@js src="js/classie.js,js/main3.js" />
</body>
</html>