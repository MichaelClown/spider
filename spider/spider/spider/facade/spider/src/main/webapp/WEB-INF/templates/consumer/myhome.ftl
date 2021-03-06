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
    <@js src="js/jquery.min.js,js/bootstrap.min.js,js/snap.svg-min.js" />
    <!--必要样式-->
    <@css href="css/menu_elastic.css" />
    <!--[if IE]></script>
    <![endif]-->
</head>
<body class="huibg">
<div class="vipcenter">
    <div class="vipheader">
        <a href="userinfo.html">
            <div class="touxiang"><img src="images/tx.jpg" alt=""></div>
            <div class="name">${(userName)!}</div>
            <div class="gztt">认证会员，已关注</div>
        </a>
    </div>
    <div class="vipsan">
        <div class="col-xs-4 text-center"><a ><h4>等级</h4><p>Vip1</p></a></div>
        <div class="col-xs-4 text-center"><a ><h4>积分</h4><p>1200</p></a></div>
        <div class="col-xs-4 text-center"><a ><h4>领取码</h4><p>3</p></a></div>
    </div>
    <ul class="vipul">
        <li>
            <a href="#">
                <div class="icc"><i class="iconfont icon-xitongmingpian"></i></div>
                <div class="lzz">会员认证</div>
                <div class="rizi lvzi">已认证</div>
            </a>
        </li>
        <li>
            <a href="#">
                <div class="icc"><i class="iconfont icon-huodongfj"></i></div>
                <div class="lzz">活动中心</div>
                <div class="rizi"><i class="iconfont icon-jiantouri"></i></div>
            </a>
        </li>
        <li>
            <a href="ddcenter.html">
                <div class="icc"><i class="iconfont icon-liebiao"></i></div>
                <div class="lzz">订单中心</div>
                <div class="rizi"><i class="iconfont icon-jiantouri"></i></div>
            </a>
        </li>
        <li>
            <a href="userinfo.html">
                <div class="icc"><i class="iconfont icon-yonghux"></i></div>
                <div class="lzz">个人信息</div>
                <div class="rizi"><i class="iconfont icon-jiantouri"></i></div>
            </a>
        </li>
        <li>
            <a href="dizhi.html">
                <div class="icc"><i class="iconfont icon-chakangonglve"></i></div>
                <div class="lzz">收货地址</div>
                <div class="rizi"><i class="iconfont icon-jiantouri"></i></div>
            </a>
        </li>
    </ul>
</div>
<div class="footnav">
    <div class="col-xs-3 text-center"><a href="index.html"><i class="iconfont icon-shop"></i><p>积分商城</p></a></div>
    <div class="col-xs-3 text-center"><a href="#"><i class="iconfont icon-huodongfj"></i><p>活动中心</p></a></div>
    <div class="col-xs-3 text-center"><a href="ddcenter.html"><i class="iconfont icon-liebiao"></i><p>订单中心</p></a></div>
    <div class="col-xs-3 text-center"><a href="mendian.html"><i class="iconfont icon-dizhi"></i><p>附近门店</p></a></div>
</div>


<@js src="js/classie.js, js/main3.js"/>
</body>
</html>