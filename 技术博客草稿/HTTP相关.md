HTTP相关

### HTTP请求报文

一个HTTP请求报文由请求行（request line）、请求头部（header）、空行和请求数据4个部分组成，下图给出了请求报文的一般格式。
[![img](http://upload-images.jianshu.io/upload_images/3985563-cd59a3899ef546e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-cd59a3899ef546e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 1.请求行

请求行分为三个部分：请求方法、请求地址和协议版本	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           

**请求方法**

HTTP/1.1 定义的请求方法有8种：GET、POST、PUT、DELETE、PATCH、HEAD、OPTIONS、TRACE。

OPTIONS：返回服务器针对特定资源所支持的HTTP请求方法。也可以利用向Web服务器发送'*'的请求来测试服务器的功能性。 

HEAD：向服务器索要与GET请求相一致的响应，只不过响应体将不会被返回。这一方法可以在不必传输整个响应内容的情况下，就可以获取包含在响应消息头中的元信息。 

GET：向特定的资源发出请求。 

POST：向指定资源提交数据进行处理请求（例如提交表单或者上传文件）。数据被包含在请求体中。POST请求可能会导致新的资源的创建和/或已有资源的修改。 

PUT：向指定资源位置上传其最新内容。 

DELETE：请求服务器删除Request-URI所标识的资源。 

TRACE：回显服务器收到的请求，主要用于测试或诊断。 

CONNECT：HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。

**最常的两种是GET和POST，下面重点讲下两者区别**：

GET和POST是什么？HTTP协议中的两种发送请求的方法。

HTTP是什么？HTTP是基于TCP/IP的关于数据如何在万维网中如何通信的协议。

HTTP的底层是TCP/IP。所以GET和POST的底层也是TCP/IP，也就是说，GET/POST都是TCP链接。GET和POST能做的事情是一样一样的。你要给GET加上request body，给POST带上url参数，技术上是完全行的通的。

在我大万维网世界中，TCP就像汽车，我们用TCP来运输数据，它很可靠，从来不会发生丢件少件的现象。但是如果路上跑的全是看起来一模一样的汽车，那这个世界看起来是一团混乱，送急件的汽车可能被前面满载货物的汽车拦堵在路上，整个交通系统一定会瘫痪。为了避免这种情况发生，交通规则HTTP诞生了。HTTP给汽车运输设定了好几个服务类别，有GET, POST, PUT, DELETE等等，HTTP规定，当执行GET请求的时候，要给汽车贴上GET的标签（设置method为GET），而且要求把传送的数据放在车顶上（url中）以方便记录。如果是POST请求，就要在车上贴上POST的标签，并把货物放在车厢里。当然，你也可以在GET的时候往车厢内偷偷藏点货物，但是这是很不光彩；也可以在POST的时候在车顶上也放一些数据，让人觉得傻乎乎的。HTTP只是个行为准则，而TCP才是GET和POST怎么实现的基本。

但是，我们只看到HTTP对GET和POST参数的传送渠道（url还是requrest body）提出了要求。“标准答案”里关于参数大小的限制又是从哪来的呢

在我大万维网世界中，还有另一个重要的角色：运输公司。不同的浏览器（发起http请求）和服务器（接受http请求）就是不同的运输公司。 虽然理论上，你可以在车顶上无限的堆货物（url中无限加参数）。但是运输公司可不傻，装货和卸货也是有很大成本的，他们会限制单次运输量来控制风险，数据量太大对浏览器和服务器都是很大负担。业界不成文的规定是，（大多数）浏览器通常都会限制url长度在2K个字节，而（大多数）服务器最多处理64K大小的url。超过的部分，恕不处理。如果你用GET服务，在request body偷偷藏了数据，不同服务器的处理方式也是不同的，有些服务器会帮你卸货，读出数据，有些服务器直接忽略，所以，虽然GET可以带request body，也不能保证一定能被接收到哦。

好了，现在你知道，GET和POST本质上就是TCP链接，并无差别。但是由于HTTP的规定和浏览器/服务器的限制，导致他们在应用过程中体现出一些不同。 

GET和POST还有一个重大区别，简单的说：

GET产生一个TCP数据包；POST产生两个TCP数据包。

长的说：

对于GET方式的请求，浏览器会把http header和data一并发送出去，服务器响应200（返回数据）；

而对于POST，浏览器先发送header，服务器响应100 continue，浏览器再发送data，服务器响应200 ok（返回数据）。

也就是说，GET只需要汽车跑一趟就把货送到了，而POST得跑两趟，第一趟，先去和服务器打个招呼“嗨，我等下要送一批货来，你们打开门迎接我”，然后再回头把货送过去。

因为POST需要两步，时间上消耗的要多一点，看起来GET比POST更有效。因此Yahoo团队有推荐用GET替换POST来优化网站性能。但这是一个坑！跳入需谨慎。为什么？

\1. GET与POST都有自己的语义，不能随便混用。

\2. 据研究，在网络环境好的情况下，发一次包的时间和发两次包的时间差别基本可以无视。而在网络环境差的情况下，两次包的TCP在验证数据包完整性上，有非常大的优点。

\3. 并不是所有浏览器都会在POST中发送两次包，Firefox就只发送一次。



**请求地址**

URL:统一资源定位符，是一种自愿位置的抽象唯一识别方法。

组成：<协议>：//<主机>：<端口>/<路径>

**端口和路径有时可以省略（HTTP默认端口号是80）**

如下例：
[![img](http://upload-images.jianshu.io/upload_images/3985563-54ce5eca048253be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-54ce5eca048253be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
有时会带参数，GET请求

**协议版本**

协议版本的格式为：HTTP/主版本号.次版本号，常用的有HTTP/1.0和HTTP/1.1

##### 2.请求头部

请求头部为请求报文添加了一些附加信息，由“名/值”对组成，每行一对，名和值之间使用冒号分隔。

常见请求头如下：
[![img](http://upload-images.jianshu.io/upload_images/3985563-539378eee14fa322.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-539378eee14fa322.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
请求头部的最后会有一个空行，表示请求头部结束，接下来为请求数据，这一行非常重要，必不可少。

##### 3.请求数据

可选部分，比如GET请求就没有请求数据。

下面是一个POST方法的请求报文：

> POST 　/index.php　HTTP/1.1 　　 请求行
> Host: localhost
> User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:10.0.2) Gecko/20100101 Firefox/10.0.2　　请求头
> Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
> Accept-Language: zh-cn,zh;q=0.5
> Accept-Encoding: gzip, deflate
> Connection: keep-alive
> Referer: [http://localhost/](http://localhost/)
> Content-Length：25
> Content-Type：application/x-www-form-urlencoded
> 　　空行
> username=aa&password=1234　　请求数据







### HTTP响应报文

[![img](http://upload-images.jianshu.io/upload_images/3985563-c6ee8f8526f59fc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-c6ee8f8526f59fc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
HTTP响应报文主要由状态行、响应头部、空行以及响应数据组成。

##### 1.状态行

由3部分组成，分别为：协议版本，状态码，状态码描述。

其中协议版本与请求报文一致，状态码描述是对状态码的简单描述，所以这里就只介绍状态码。

**状态码**

状态代码为3位数字。
1xx：指示信息–表示请求已接收，继续处理。
2xx：成功–表示请求已被成功接收、理解、接受。
3xx：重定向–要完成请求必须进行更进一步的操作。
4xx：客户端错误–请求有语法错误或请求无法实现。
5xx：服务器端错误–服务器未能实现合法的请求。

下面列举几个常见的：

[![img](http://upload-images.jianshu.io/upload_images/3985563-8f3bf059bc4365e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-8f3bf059bc4365e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 2.响应头部

与请求头部类似，为响应报文添加了一些附加信息

常见响应头部如下：

[![img](http://upload-images.jianshu.io/upload_images/3985563-33ed95479f541a07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](http://upload-images.jianshu.io/upload_images/3985563-33ed95479f541a07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 3.响应数据

用于存放需要返回给客户端的数据信息。

下面是一个响应报文的实例：

> HTTP/1.1 200 OK　　状态行
> Date: Sun, 17 Mar 2013 08:12:54 GMT　　响应头部
> Server: Apache/2.2.8 (Win32) PHP/5.2.5
> X-Powered-By: PHP/5.2.5
> Set-Cookie: PHPSESSID=c0huq7pdkmm5gg6osoe3mgjmm3; path=/
> Expires: Thu, 19 Nov 1981 08:52:00 GMT
> Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0
> Pragma: no-cache
> Content-Length: 4393
> Keep-Alive: timeout=5, max=100
> Connection: Keep-Alive
> Content-Type: text/html; charset=utf-8
> 　　空行
>
> 　　响应数据
>
> 
>
> 
>
> 
>
> 
>
> Hello HTTP!
>
> 
>
> 

关于请求头部和响应头部的知识点很多，这里只是简单介绍。

通过以上步骤，数据已经传递完毕，HTTP/1.1会维持持久连接，但持续一段时间总会有关闭连接的时候，这时候据需要断开TCP连接。



# [cookie属性详解](http://www.cnblogs.com/tzyy/p/4151291.html)

![img](http://images.cnitblog.com/blog/450613/201412/081529469931522.jpg)

在chrome控制台中的resources选项卡中可以看到cookie的信息。

一个域名下面可能存在着很多个cookie对象。

**name**字段为一个cookie的名称。

**value**字段为一个cookie的值。

**domain**字段为可以访问此cookie的域名。

非顶级域名，如二级域名或者三级域名，设置的cookie的domain只能为顶级域名或者二级域名或者三级域名本身，不能设置其他二级域名的cookie，否则cookie无法生成。

顶级域名只能设置domain为顶级域名，不能设置为二级域名或者三级域名，否则cookie无法生成。

二级域名能读取设置了domain为顶级域名或者自身的cookie，不能读取其他二级域名domain的cookie。所以要想cookie在多个二级域名中共享，需要设置domain为顶级域名，这样就可以在所有二级域名里面或者到这个cookie的值了。
顶级域名只能获取到domain设置为顶级域名的cookie，其他domain设置为二级域名的无法获取。

**path**字段为可以访问此cookie的页面路径。 比如domain是abc.com,path是/test，那么只有/test路径下的页面可以读取此cookie。

**expires/Max-Age** 字段为此cookie超时时间。若设置其值为一个时间，那么当到达此时间后，此cookie失效。不设置的话默认值是Session，意思是cookie会和session一起失效。当浏览器关闭(不是浏览器标签页，而是整个浏览器) 后，此cookie失效。

**Size**字段 此cookie大小。

**http**字段  cookie的httponly属性。若此属性为true，则只有在http请求头中会带有此cookie的信息，而不能通过document.cookie来访问此cookie。

**secure **字段 设置是否只能通过https来传递此条cookie



### 参考链接

[99% 的人都理解错了 HTTP 中 GET 与 POST 的区别](https://juejin.im/entry/57597bd45bbb500053c88b4c)