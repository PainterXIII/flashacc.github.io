<?php
/*
第一修改者为作者
修改者:lbr-dev,预留
修改者GITHUB:lbr-dev(https://github.com/lbr-dev/),预留
修改者QQ:lbr-dev(3170482764),预留
本项目开源地址:https://github.com/kingcardconfig/kingcardconfig/
*/

include_once(dirname(dirname(__FILE__)).'/public/public.php');

$kingcardconfigcheckupdate=gethtml($configkingcardconfigcheckupdateurl,$configkingcardconfigcheckupdateua,$configtimeout,KCC_ROOT."tmps/".$configisgziphtmlfilename);
$explode=explode('|',$kingcardconfigcheckupdate);

if ($explode[0] > $versionnumber) {
$zip=gethtml($explode[2],$configkingcardconfigcheckupdateua,$configtimeout,KCC_ROOT."tmps/".$configisgziphtmlfilename);
$fp = fopen(KCC_ROOT.'tmps/zip.zip', 'w');
fwrite($fp, $zip);
fclose($fp);
unzip(KCC_ROOT."tmps/zip.zip");
unlink(KCC_ROOT."tmps/zip.zip");
echo "更新中！";
} else {
echo "没有更新！";
}

?>