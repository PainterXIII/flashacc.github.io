<?php
/*
第一修改者为作者
修改者:lbr-dev,预留
修改者GITHUB:lbr-dev(https://github.com/lbr-dev/),预留
修改者QQ:lbr-dev(3170482764),预留
本项目开源地址:https://github.com/kingcardconfig/kingcardconfig/
*/
include_once(dirname(dirname(__FILE__)).'/public/public.php');
$get=$_GET;
$apitk=$get["apitk"];
$txt=kccd($apitk,$configkccedkey);
$explode=explode(',',$txt);
$user=fgc(KCC_ROOT."files/user/".$explode[0]);
$userexplode=explode(',',$user);
if ($explode[0] == $userexplode[0]) {
if ($explode[1] == $userexplode[1]) {
if ($explode[2] == $userexplode[2]) {
if ($explode[3] == getuserip()) {
if ($explode[4] == $configkccedkey) {
$wkdtconfig=fgc(KCC_ROOT."files/".$configwkdtconfigfilename);
echo $wkdtconfig;
} else {
echo "key不正确！";
}
} else {
echo "api用户服务器IP不正确！";
}
} else {
echo "api用户tk不正确！";
}
} else {
echo "api用户密码不正确！";
}
} else {
echo "api用户名不存在！";
}
?>