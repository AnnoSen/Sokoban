Java 关于paintComponent与paint有什么区别？各自的特征
重点：

1 - paint() 中调用 paintComponent(), paintBorder(), paintChildren()

2 - 最重要的区别是“双缓冲”。Swing 组件的 paint() 中实现了双缓冲，所以不要随便去覆写，会破坏双缓冲的，————建议的方式是覆写 paintComponent()，
很多人做的小程序会”闪烁“，就是因为他们覆写了 paint() 方法，破坏了Swing本身的双缓冲。Swing 不建议用户自己实现双缓冲。

3 - 覆写 paint()，如果新方法没有去调用 paintChildren()，还会造成子控件不显示，鼠标移上去才显示，这个也是很多新手问的问题： “为什么我的按钮只有鼠标移上去才显示？”

4 - 只有极少数的情况可能需要覆写 paint() 方法，通常是为了实现特殊的绘图效果，或者特殊的优化，比如 JViewport 覆写了 paint() 方法，使用“延迟重绘”的方式来合并当滚动条移动时一些特别频繁的重绘请求，等等。

5 - paint方法定义于Component类 awt画布就可以自己重写paint方法,达到自己的目的 paintComponent定义于JComponent类 JComponent类继承了Component类,
JPanel类继承了JComponent类 所以在JPanel既有paint方法,又有paintComponent方法 但是paint方法被java开发程序员重写了,进行着大量的复杂工作,如图象缓冲,
解决了awt画布的抖动等问题,所以这个方法是不能被重写的,否则你的程序没办法正常工作 于是java开发程序员写了paintComponent方法替代paint方法,让我们完成自己需要的工作,
其作用相当于awt的paint方法,作用完全一样 这2个方法都不允许自己调用,程序需要时,方法会自动调用 你要自己强制重画,需要调用repaint()方法

简单说,你用awt画布就用paint方法 你用Swing的JPanel就用paintComponent方法(Swing没有画布)
------解决方案--------------------
之间我看过一个人写的关于这个的文章，写的应该挺全的，可惜找不到地址了。。
这两个方法，
paint()是基础，paint中会调用 paintComponent(), paintBorder(), paintChildren()
这三个方法，
这三个方法一个是绘制背景，一个绘制边框，一个绘制子控件。
一般重写背景，是建议重写paintComponent()的。
===================================================================================================================================
1、通过继承JPanel并重写paintComponent()、paint()、update()方法，解决v2中同一位置出现重叠图片导致闪烁的问题
    paintComponent():通过重写该方法设置背景图
    paint():通过重写该方法加载移动组件，注意：当重写paintComponent()后，paint()方法中一定调用super.paint()，不然无法加载背景图
    update():通过重写该方法定义刷新规则