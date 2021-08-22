<?php
/*
第一修改者为作者
修改者:lbr-dev,预留
修改者GITHUB:lbr-dev(https://github.com/lbr-dev/),预留
修改者QQ:lbr-dev(3170482764),预留
本项目开源地址:https://github.com/kingcardconfig/kingcardconfig/
*/
include_once('public/public.php');
$submit = $_GET['submit'];
if ($submit == 'yes') {
$guid = $_GET['guid'];
$token = $_GET['token'];
$time=date("Y-m-d H:i:s");
$wkdtconfig='{"Time":"'.$time.'","Guid":"'.$guid.'","Token":"'.$token.'"}';
fpc(KCC_ROOT."files/".$configwkdtconfigfilename,$wkdtconfig);
echo "写入配置到".KCC_ROOT."files/".$configwkdtconfigfilename."成功！";
} else {
echo ubbtohtml($configkingcardconfigsubmitguidandtokenubborhtml);
}
?>