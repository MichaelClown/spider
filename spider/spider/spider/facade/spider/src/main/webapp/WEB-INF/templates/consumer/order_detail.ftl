<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>html</title>
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_1459473269_4751618.css">
    <@css href="css/bootstrap.min.css" />
    <@css href="css/style.css" />
    <@js src="js/jquery.min.js，js/bootstrap.min.js,js/snap.svg-min.js" />
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
    <a class="navbar-tit center-block">订单详情</a>
    <button class="topnav" id="open-button"><span class="iconfont icon-1"></span></button>
</nav>

<div class="dingdan">
    <div class="ddlist">
        <div class="dtit">订单信息</div>
        <div class="dz"><p class="ziku">电  商：</p>${(orderDetail.ecommerceCompany)!}</div>
        <div class="dz"><p class="ziku">创建时间：</p>${(orderDetail.createDate)!}</div>
    </div>
    <div class="ddlist">
        <div class="dtit">收货信息</div>
        <div class="dzdv">
            <span class="name">${(orderDetail.destinationActor.realName)!}</span> <span class="phone">${(orderDetail.destinationActor.cellPhone)!}</span>
            <span class="dd">${(orderDetail.destinationAddress.province)!} ${(orderDetail.destinationAddress.city)} ${(orderDetail.destinationAddress.area)!} ${(orderDetail.destinationAddress.detail)!}</span>
            <span class="dd">邮编：${(orderDetail.destinationAddress.zipCode)!}</span>

        </div>
    </div>
    <div class="ddlist">
        <div class="dtit">订单备注</div>
        <div class="dz noblord">${(orderDetail.comment)!}</div>
    </div>
    <div class="ddlist">
        <div class="dtit">物流单号</div>
        <div class="dz noblord">${(orderDetail.logisticsCompany)!}：${(orderDetail.logisticsOrderId)!}</div>
    </div>

</div>


<@js src="js/classie.js,js/main3.js" />
</body>
</html>