<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>html</title>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_1457937316_1758883.css">
    <@css href="css/bootstrap.min.css" />
    <@css href="css/style.css" />
    <@js src="js/jquery.min.js,js/bootstrap.min.js,js/jquery.gcjs.js,js/snap.svg-min.js" />
    <!--必要样式-->
    <@css href="css/menu_elastic.css" />
    <!--[if IE]>
    <@js src="js/html5.js" />
    <![endif]-->
</head>
<body class="huibg">
<div class="menu-wrap">
    <nav class="menu">
        <div class="icon-list">
            <a href="index.html"><i class="iconfont icon-home"></i><span>首页</span></a>
            <a href="personalcenter.html"><i class="iconfont icon-yonghux"></i><span>个人中心</span></a>
            <a href="ddcenter.html"><i class="iconfont icon-liebiao"></i><span>订单中心</span></a>
            <a href="userinfo.html"><i class="iconfont icon-xitongmingpian"></i><span>个人信息</span></a>
            <a href="dizhi.html"><i class="iconfont icon-dizhi"></i><span>地址信息</span></a>
        </div>
    </nav>
    <button class="close-button" id="close-button">Close Menu</button>
    <div class="morph-shape" id="morph-shape" data-morph-open="M-1,0h101c0,0,0-1,0,395c0,404,0,405,0,405H-1V0z">
        <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 100 800" preserveAspectRatio="none">
            <path d="M-1,0h101c0,0-97.833,153.603-97.833,396.167C2.167,627.579,100,800,100,800H-1V0z"/>
        </svg>
    </div>
</div>
<nav class="navbar text-center">
    <button class="topleft" onclick ="javascript:history.go(-1);"><span class="iconfont icon-fanhui"></span></button>
    <a class="navbar-tit center-block">
        <#if addressResponse ?? && addressResponse.addressId != 0>
            编辑
        <#else>
            新增
        </#if>
        地址
    </a>
    <button class="topnav" id="open-button"><span class="iconfont icon-1"></span></button>
</nav>
</div>

<div class="usercenter">
    <form action="/consumer/address/save" method="post">
    <input name="addressId" id="addressId" hidden value="${(addressResponse.addressId)!}">
        <div class="cdv">
            <input type="text" name="userName" id="userName" value="${(addressResponse.userName)!}">
        </div>
        <div class="cdv">
            <input type="text" name="cellPhone" id="cellPhone" value="${(addressResponse.cellPhone)!}">
        </div>
        <div class="cdv">
            <!-- sel-provance -->
            <select id="s_province" name="s_province"></select><br>
        </div>
        <div class="cdv">
            <select id="s_city" name="s_city" ></select><br><!-- sel -->
        </div>
        <div class="cdv">
            <!-- sel-area -->
            <select id="s_county" name="s_county"></select><br>
        </div>
        <script type="text/javascript" src="js/area.js"></script>
        <script type="text/javascript">_init_area();</script>
        <div class="cdv">
            <input type="text" name="detail" id="detail" value="${(addressResponse.detail)!}">
        </div>
        <div class="cdv">
            <input type="text" name="zipCode" id="zipCode" value="${(addressResponse.zipCode)!}">
        </div>
        <div class="cdv">
            <input type="text" name="companyName" id="companyName" value="${(addressResponse.companyName)!}">
        </div>
        <input type="submit" class="btnlg">提 交</input>
    </form>
</div>

<@js src="js/classie.js,js/main3.js" />
</body>
</html>