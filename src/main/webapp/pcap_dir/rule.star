alert tcp any -> 80 (msg:"jinshanciba";flow:to_server,isfirst;content:"dict-client.iciba.com",http_host,lss,pos:end;protocol:http;priority:4;service:jinshanciba;sid:20116;)
alert tcp any -> 80 (msg:"jinshanciba";flow:to_server,isfirst;content:"interface2010.client.iciba.com",http_host,lss,pos:end;protocol:http;priority:4;service:jinshanciba;sid:20117;)
alert tcp any -> 80 (msg:"jinshanciba";flow:to_server,isfirst;content:"ciba.count.www.iciba.com",http_host,lss,pos:end;protocol:http;priority:4;service:jinshanciba;sid:20118;)
alert tcp any -> 80 (msg:"jinshanciba";flow:to_server,isfirst; content:"POST",nocase,depth:4;content:"/fy/api.php",nocase,pos:start,http_url;content:"dict-mobile.iciba.com",nocase,lss,http_host;protocol:http;priority:4;service:jinshanciba;sid:20119;)
alert tcp any -> 443 (msg:"iDrive-Web"; flow:to_server,established; content:"|16|",depth:1; content:"|01|",offset:5,depth:1; content:".iDrive.com",lss,nocase; service:iDrive-Web; sid:20220;)
alert tcp 443 -> any (msg:"iDrive-Web"; flow:to_client,established; content:"|16|",depth:1; content:"|02|",offset:5,depth:1; content:".iDrive.com",lss,nocase; service:iDrive-Web; sid:20221;)

