# Netty-Demo
First example-2019年5月10日09:16:11-客户端和服务端实现简单通信

Thrid example-2019年5月11日15:27:42-多个客户端与服务端实现广播通信

## 1. Netty-Demo 多个客户端与服务端实现广播通信
重点内容

### 1. channelGroup
   存储多个连接channel,根据channelGroup实现与多个客户端发送消息,具体业务代码
   
      channelGroup.forEach(ch->{
            if (channel!=ch) {
                ch.writeAndFlush(channel.remoteAddress() + "[别人发送的消息]" + msg+"\n");
            }else {
                ch.writeAndFlush( "[自己发送的消息]" + msg+"\n");
            }
        });
### 2. 客户端输入方式
客户端通过system.in()输入,通过channel发送消息   
    
     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            //客户端输入,把bufferedReader数据放到管道输出
            for (; ; ) {
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
            }
            
### 3. writeAndFlush和write

writeAndFlush 表示把数据从缓冲区发送到客户端,write表示发送到缓冲区,并没有发送到客户端

### 4. 重载SimpleChannelInboundHandler方法

   void handlerAdded(ChannelHandlerContext ctx)-->表示客户端和服务端建立好连接
   
   void handlerRemoved(ChannelHandlerContext ctx)--->连接断开
   
   void channelActive(ChannelHandlerContext ctx)---->连接处于活动状态
   
   void channelInactive(ChannelHandlerContext ctx)----->表示连接下线
   
   void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)---->出现异常
   
   void channelRead0(ChannelHandlerContext ctx, String msg)--->具体执行业务逻辑部分,也就是连接成功之后必须走的方法
   
   
 
## 2. Netty-Demo 心跳机制

主要更改的地方在服务端

在重写void initChannel(SocketChannel ch)方法中,引入IdleStateHandler()方法,他表示空闲状态监测处理器

在具体的handler处理器中extends的是ChannelInboundHandlerAdapter,也就是SimpleChannelInboundHandler的父类

重写userEventTriggered(ChannelHandlerContext ctx, Object evt)方法,我们根据返回的状态来判断服务端是什么状态

发起人是ping,返回成为pong

 
    @Override
     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时操作:"+eventType);
            ctx.channel().close();
        }
    }



## 3. Netty-Demo 实现webSocket功能
1重要的handler
WebSocketServerProtocolHandler

----------
# IO 装饰器模式 #

核心组件

在com.judy.decorator.mydecorator 包下是我自己手写的装饰器模式.

1 被装饰者 

2 装饰者公共类

3 具体装饰者,基础的装饰

4 在基础的装饰上,增加装饰者实现1

5 在基础的装饰上,增加装饰者实现2

> 装饰者主要的功能就是可以实现动态的添加职责,也就是我们的4和5


装饰者主要的两点体现

1 继承父类的构造函数,传入基础装饰

2 构建具体的装饰,也就是基础装饰

3 继承被装饰类实现自己特有的被装饰者



----------

# Nio #
下面我将要从IO到Nio分别介绍他们的关键点,这部分的代码在com.judy.nio包下面总共分为12个Demo,最后演示了一个客户端和服务器端实现通过Nio进行通信过程,我会从每个demo入手,然后依次讲解知识点 , 介绍Nio中的核心:Selector , Channel , Buffer. 
   

1. Selector中核心属性: SelectorKeys,事件.

2. Channel: ServerSocketChannel, SocketChannel. 

3. Buffer: position ,limit,  capacity之间的关系.

4. Buffer中的方法flip(),clear().以及底层方法HeapByteBuffer DirectByteBuffer之间的关系, Buffer是如何与数据进行交互?

5. Buffer的数据类型总共有几种? 为什么是7种? 哪一种类型没有?

6. Buffer中底层allocate()方法做了什么?

7. Channel与Buffer是如何进行交互数据的?

8. Selector如何与Channel进行交互的? 如果实现一个线程就可以接受多个客户端的数据?

9. Selector,Channel,buffer整体流是什么?


## NioTest1 ##

主要简单简单什么是Buffer 

1 首先需要创建Buffer对象,分配的内存空间为10

    IntBuffer buffer = IntBuffer.allocate(10);

2 向Buffer对象输入数据
    
    buffer.put(randomNumber);

3 转变Buffer的状态,读写状态变化(必须写,后续介绍为什么)
  
     buffer.flip();

4 读取buffer中的数据

    System.out.println(buffer.get());
    

上面只是简单的介绍了一下Buffer是如何操作数据的, 在Nio中直接操作数据的只有Buffer,这点必须记住~ Buffer底层操作的是数组类型.

## NioTest2 ##

主要介绍从Buffer是如何从Channel中读取文件流数据

1.读取文件流
 
      FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
    
2.得到文件流的Channel,通道
     
      FileChannel channel = fileInputStream.getChannel();

3.创建buffer对象

    ByteBuffer buffer = ByteBuffer.allocate(512);

4.Buffer读取Channel中数据  
    
     channel.read(buffer);

5.Buffer状态转变
 
     buffer.flip();

6.读取Buffer中的数据

    buffer.get();


## NioTest3 ##

主要介绍Buffer写数据到Channel,写入文件(由于大部分内存相似,所以我只说核心的代码) 从Test1 和Test2 可以看出Channel至于Buffer进行交互,

 并且如果程序想要读写操作数据,必须是通过Buffer才可以, 而Channel知识操作数据而已. 

1.向Buffer中写入数据

      allocate.put(message[i]);

2.Buffer状态转换
      
     allocate.flip();

3.把Buffer中的数据写入到Channel中

     channel.write(allocate);

## NioTest4 ##

主要介绍position,limit,capacity的关系

### allocate初始化###

1 首先我们先看Buffer中allocate()方法的源码是如何实现,我选择的是intbuffer.

     public static IntBuffer allocate(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        return new HeapIntBuffer(capacity, capacity);
    }


     HeapIntBuffer(int cap, int lim) { 
        super(-1, 0, lim, cap, new int[cap], 0);     
    }


    IntBuffer(int mark, int pos, int lim, int cap,   // package-private
                 int[] hb, int offset)
    {
        super(mark, pos, lim, cap);
        this.hb = hb;
        this.offset = offset;
    }


      Buffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
        limit(lim);
        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                                                   + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }  


我们知道capacity表示的意思是初始化buffer容量,从上面代码我们可以看出我们初始化的值最终赋给了cap. 而cap表示的意思就是buffer数组的最大容量,并且这个容量是不可以再更改的;
   
lim 的值在初始化的时候与cap是同等大小;

pos 的值在初始化的时候值为0;

### pos ,lim .cap 改变互相牵引 ###

1 buffer中存放数据

    buffer.put(randomNumber);

> position的值加1

2 buffer转换读写状态

    buffer.filp()

3 查看buffer中limit的大小 

    buffer.limit();

> limit的值变成1， position变为0， cap的值不变

通过NioTest4 的代码我们会发现limit和position和cap之间的 当buffer写入的时候pos的值 一直add. 当buffer转换状态的时候, pos要回到索引为0的位置, limit要到pos的位置, 也就是我们读取buffer中数据的时候只能呢读到limit的索引位置, 而cap表示我们最大的数组剩余的空间容量



## NioTest5 ##

Buffer中的数据类型.除啦Boolean类型没有其他都有

## NioTest6,7 ##
 
Buffer的分割和切片,Buffer只可以设置只读,看代码就会

## NioTest8 ##

主要是buffer底层是如何操作的

在Buffer中我们知道初始化数据有两种数据,第一个是allocate()方法,第二是allocateDirect() 他们的两个不同的地方在于allocate是数据放到堆内存上, 而allocateDirect()把数据放到jvm之外, 但是最终代码执行都是到jvm之外,跟踪源码的时候会发现address,为什么把他发到父类,原因是因为初始化加载的时候效率提高. 
       
    // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
    long address;



## NioTest9 ##

主要讲的是buffer的另一种特性直接跟内存打交道,不对磁盘直接做操作,减少了开销,性能得到优化


## NioTest12 ##

channel是和selector如何交互了,在io上面提升了什么? 为什么Nio是异步的?


1 创建Selector

     Selector selector = Selector.open();

2 创建服务端SeverSocketChannel

     ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

3 设置为非阻塞

     serverSocketChannel.configureBlocking(false);

4 得到ServerSocket套接字,然后接受端口号

    InetSocketAddress address = new InetSocketAddress(ports[i]);

> 注意:这里的端口号只是针对的客户端和服务端连接的端口号,但是针对发送数据的时候,他的端口号会自己变化.

5 Channel注册到Selector上(这一步是重点)
    
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

> 首先解释一下serverSocketChannel他的意思就是连接客户端channel, 这就话的意思就是我们把连接客户端事件的channel注册到Selector上,事件是SelectionKey.OP_ACCEPT

6 获取连接数量

     int select = selector.select();

> select是阻塞的连接,也就是当客户端端不连接,或者说没有事件的时候,他会一直阻塞,他返回的是连接的数量


7  SelectionKey是什么?
    
    Set<SelectionKey> selectionKeys = selector.selectedKeys();
    
> SelectionKey是什么?

>  是一个事件

>  每次当channel注册到selector的时候都会出现一个SelectionKey

>  为什么会有SelectionKey?

>  通过SelectionKey可以获取Selector已经注册到Channel上发生的事件
 
>  通过SelectionKey可以后去Channel,可以与客户端进行交互

>  什么时候创建SelectionKey?
  
>  在Channel注册到selector的时候会创建一个Channel


8 从SelectionKey中获取channel

  ` ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();`

9 务端需要返回给客户端一个serverSocket对象(表示客户端已经跟服务端建立好了连接)-->所以之后的操作都是针对的SocketChannel对象(serverSocketChannel对象现在的任务已经完成了,用不上了(就是起到建立连接))

    client = serverSocketChannel.accept();

10  配置为非阻塞的

    client.configureBlocking(false);

11 客户端SocketChannel连接到selector

 client.register(selector, SelectionKey.OP_READ);



> 目前Selector上面注册了两个Channel对象,一个是ServerSocketChannel,另一个是SocketChannel,他们两个的作用是不一样的



> ServerSocketChannel关注的事件是连接,SocketChannel关注的事件是客户端和服务器端读取事件

12 声明buffer读取获取写数据 然后交给指定的Channel进行发送数据(下面的代码只是伪代码,具体代码看NIoTest12或者NioServer)

      client = (SocketChannel) selectionKey.channel();
      ByteBuffer readBuffer = ByteBuffer.allocate(1024);
      int readCount = client.read(readBuffer);
      readBuffer.flip();
      writeBuffer.put((senderKey + ":" + receivedMessage).getBytes());

## NIoClient与NIoServer有什么不同?

1 在client不是bind端口,而是连接

       InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8888);
       socketChannel.connect(inetSocketAddress);

2 当Channel注册到Selector,服务端和客户端建立了连接,我们需要做判断是否连接成功

      `selectionKey.isConnectable()如果为true则表示建立了连接`
       clientChannel.isConnectionPending() 如果为ture则表示连接在Channel中是挂起也就是正在进行的状态

 

> 其他的操作跟客户端都差不多


## Nio总体分析 ##
  
   

> 多个 Channel把事件注册到Selector, Selector来进行监听, 当有数据的时候 Channel会把数据给Buffer读取.,当然在这个过程中是在while循环中


# Reactor #

## 传统网络调用顺序?
多个client客户端对应一个服务端, 服务端需要处理多个handler, 则一个client就是一个线程

也就是一个socket对应一个服务器的线程, 如果这次连接已经完成但是没有释放,则socket会一直占用资源

## 什么是Reactor ?
1 事件驱动

2 多路复用

3 根据注册的事件进行回调


## Reactor 单线程模型  ?

单线程下主要做的任务是监听客户端与服务端的连接,服务端把客户端的数据分发给指定的处理器执行

##Reactor 多线程模式 ?
为什么会出现多线程?

因为Reactor负责的是监听客户端的连接,而dispatch负责的是分发事件并且做业务处理, 所以导致 Reactor和dispatch 

他们两个之间的速度不匹配, 也就是Reactor完事了但是dispatch还在阻塞状态, 所以利用线程池来进行他们之间不匹配问题

在dispatch分发的时候我们创建了一个线程池, 也就是一个Reactor对于dispatch的线程池


## Reactor主从模型 ?

主从模型主要突出的是一个boss一个work,boss用于监听客户端的连接,work用于做业务处理, work使用线程池来进行管理

## reactor 角色 ?

Reactor总共有5中角色


### 1. synchronous Event Demultiplexer
同步事件分发器
1 用于等待一个事件发生
2 他指的是I/O多路复用


### 2.Initiation Dispatcher

他表示初始化分发器
1 他定义了规范

2 提供事件处理器的注册和删除
   
    2.1 注册指定的是 事件处理器他事件注册到 Initiation Dispatch上,然后等到事件发生的时候会回调事件处理器 event Handler
    

3 她会分离每一个事件(selectorkey),然后调用事件处理器进行处理

### 3.Handler句柄
表示事件,是事件的发源地, 一般是由操作系统来生成的,例如当发送连接的时候,他就会产生一个由操作系统产生的Handler句柄,文件

### 4.Event Handler

他表示事件处理器,是一个接口,他提供了很多回调方法, 注意在Nio的时候并不没有回调方法,但是在Netty中是有回调方法的

### 5.Convert EventHandler

他表示事件处理器的具体实现类, 也就是我自定义的事件处理器, 在netty里面表示的是我们自己定义的事件处理器


## netty 模式的流程?
1 首先多个客户端发送请求, 然后 通过Selector(他会不断的循环, 因为要检测客户端发送过来的连接 )连接请求Boss

2 . boss监听的是accept连接请求, 当建立连接之后, 我们可以得到selector的selectionKey 
 
3 . SelectionKey维护了SocketChannel, SocketChanne被netty封装成了 被NioSocketChann1
 
4 . 然后NioSocketChannel注册到worker的Selector上,然后监听read事件


> 注意点: 如果我们要做具体的业务逻辑,并且非常耗时的时候我们需要在handler里面使用任务调度,这样可以导致我们不阻塞



# netty 对Nio的改进

1新增了readindex 和 writeindex , 读写转换不需要filp()



# ChannelHandler与ChannelPipeline和ChannelContext之间的关系


ChannelPipeline 首先包含的多个ChannelContext,ChannelContext里面有很多ChannelHandler, ChannelContext作用就是让两个ChannelHandler之间有关联



# nettyNetty有单线程和多线程
断,当前线程与传过来的线程是否相等,如果不相等则使用线程调度方式执行,也就是执行任务,相当于使用的是thread执行,但是由于是调度的方式,会交给操作系统去执行

可以看出一个Channel值对应一个eventloop,,而一个eventLoop对应对多个Channel. 而eventloop对应一个线程执行
Channelpipeline里面虽然有很多handler,其实也是一个线程去执行的,所以netty设计不到高并发的概念,也是netty的核心概念

    @Override
    public boolean inEventLoop(Thread thread) {
     return thread == this.thread;
    }
    
    Netty线程模型
    1 一个EventLoopGroup当中会包含一个或多个EventLoop, 1对多关系
    2一个EventLoop在他的整个生命周期当中都只会与唯一一个Thread进行绑定  1对1关系
    private volatile Thread thread;
    3所有由EventLoop所处理的各种I/O事件都将在他所关联的thread上进行处理
    4一个Channel在他的整个生命周期中只会注册一个EventLoop上
    /**
     * Returns {@code true} if the {@link Channel} is registered with an {@link EventLoop}.
     */
    boolean isRegistered();
    5 一个EventLoop在运行过程中,会被分配给一个或者多个Channel
    
> addLast(Handler), ChannelHandler中的回调方法都是I/O线程所执行,如果调用了ChannelPipeline addLast(EventExecutorGroup group , ChannelHandler... handler)方法, 那么ChannelHandler中的回调方法就是由参数中的group线程组来执行