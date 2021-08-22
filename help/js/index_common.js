/**
 * 监听屏幕宽度自适应
 */
var windowWidth = $(window).width();
var max_windowWidth = 480;
// console.log(windowWidth);
window.addEventListener('load', function() {

    window.addEventListener('resize', function() {
        windowWidth = window.innerWidth;
        // console.log('qw'+window.innerWidth);
    })
})

/**
 * 底部
 * @type {number}
 */
var windowInnerHeight = window.innerHeight;
//获取当前浏览器窗口高度
$(window).resize(function(){
    if(window.innerHeight < windowInnerHeight){
        $('.weui-footer').hide();
    }else{
        $('.weui-footer').show();
    }
});



/**
 * 返回上一页
 */
function btn_referrer() {
    document.referrer === '' ?
        window.location.href = '/index/user/profile' :
        window.history.go(-1);
}

/**
 * 新版app内判断
 */
var fasInterface = window.fasInterface;
$(function () {
    if(fasInterface){
        $('.go_line').hide();
    }
})

/**
 * 老版本APP内判断
 */
var OldInterface = window.myObj;

/**
 * 截取两个字符串中间的值
 * var str = '高中10人;专科20人;本科30人;';//取10或20或30
 * var firstStr='高中';
 * var secondStr='人';
 * 走下面的js方法取到10
 * @param str
 * @param firstStr
 * @param secondStr
 * @returns {string}
 */
function getStrSection(str,firstStr,secondStr){
    if(str == "" || str == null || str == undefined){ // "",null,undefined
        return "";
    }
    if(str.indexOf(firstStr)<0){
        return "";
    }
    var subFirstStr=str.substring(str.indexOf(firstStr)+firstStr.length,str.length);
    var subSecondStr=subFirstStr.substring(0,subFirstStr.indexOf(secondStr));
    return subSecondStr;
}

/**
 * 是否是微信浏览器
 * @returns {boolean}
 */
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}


/**
 * 是否是PC端访问
 * @returns {boolean}
 */
function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}