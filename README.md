# short-url
---
 ## 1. 算法原理分析
---
1. 什么是短网址  
    即将较长的普通请求链接转换为较短的请求链接.  
    如(新浪短网址服务生成):  
       原地址:https://cn.bing.com/search?q=%e7%9f%ad%e7%bd%91%e5%9d%80%e6%9c%8d%e5%8a%a1&qs=HS&pq=%e7%9f%ad%e7%bd%91%e5%9d%80&sc=8-3&cvid=3649594CAEA64898B971A15290DB0B01&FORM=QBRE&sp=1  
       处理后:http://t.cn/RgRjigS  

2. 优点  
    简短、美观、利于传播等，同时浏览器对请求的长度是有限制的.  
    
3. 请求原理  
    (1) DNS服务器通过请求解析到 http://t.cn 后,并获得其对应 IP .  
    (2) DNS服务器获得 IP 后,想该服务器发送 GET 请求查询 RgRjigS 短码对应的原 URL .  
    (3) http://t.cn 通过 RgRjigS 查询对应原 URL, 通过重定向到原请求地址.这里可通过 301 或 302 重定向.  
   
4. 算法分析  
    
    
