<?php
require "api.php";//引用curl方法类
header('Last-Modified: '.gmdate('D, d M Y H:i:s', $time).' GMT');//说明请求时间
header('Content-Type: text/html; charset=utf-8');//说明格式
$config = my_curl("https://flashproxy.cn/WG/1.txt",[
    'ua'=>'MoYuan'
]);
$contributor = "<br>★★★★★感谢@Lin★★★★★<br>
★★★★★感谢@墨渊★★★★★<br>
★★★★★本api对接官方★★★★★<br>
★★★★★by昊天★★★★★";
$de_json = json_decode($config,true);
$clientKey = $de_json['clientKey'];
$clientIp = $de_json['clientIp'];
$serverKey = $de_json['serverKey'];
$serverIp = $de_json['serverIp'];
$clientDns = $de_json['clientDns'];
$echo = '[Interface]<br>
Address = '.$clientIp.'<be>
<br>DNS = '.$clientDns.'
<br>PrivateKey = '.$clientKey.'
<br>[Peer]<br>
AllowedIPs = 0.0.0.0/0
<br>Endpoint = '.$serverIp.'
<br>PublicKey = '.$serverKey;

echo "$echo";
echo "$contributor";
?>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">