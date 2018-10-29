# 说明

    本手册的旨在码出高效，码出质量。现代软件架构的复杂性需要协同开发完成，如何高效地协同呢？
    无规矩不成方圆，无规范难以协同，比如，制订交通法规表面上是要限制行车权，实际上是保障公众的人身安全，
    试想如果没有限速，没有红绿灯，谁还敢上路行驶。对软件来说，适当的规范和标准绝不是消灭代码内容的创造性、优雅性，
    而是限制过度个性化，以一种普遍认可的统一方式一起做事，提升协作效率，降低沟通成本。
    代码的字里行间流淌的是软件系统的血液，质量的提升是尽可能少踩坑，杜绝踩重复的坑，切实提升系统稳定性，码出质量。

# 编码规范

## 命名风格

1.【强制】代码中的命名均不能以下划线或美元符号开始，也不能以下划线或美元符号结束。
```java
反例：_name/$name/name_/name$
```
2.【强制】代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。
    说明：正确的英文拼写和语法可以让阅读者易于理解，避免歧义。注意，即使纯拼音命名方式也要避免采用。
```java
正例：cifm / weixin 等国际通用的名称，可视同英文。
反例：DaZhePromotion [打折] / getPingfenByName() [评分] / int 某变量 = 3
```
3.【强制】类名使用UpperCamelCase风格，但以下情形例外：DO / BO / DTO / VO / AO / PO / UID等。
```java
正例：MarcoPolo / UserDO / XmlService / TcpUdpDeal / TaPromotion 
反例：macroPolo / UserDo / XMLService / TCPUDPDeal / TAPromotion
```
4.【强制】方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式。
```java 
正例： localValue / getHttpMessage() / inputUserId
```
5.【强制】常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长。 
```java
正例：MAX_STOCK_COUNT 
反例：MAX_COUNT
```
6.【强制】抽象类命名使用Abstract或Base开头；异常类命名使用Exception结尾；测试类命名以它要测试的类的名称开始，以Test结尾。 

7.【强制】类型与中括号紧挨相连来 表示 数组。 
```java
正例： 定义整形数组 int[] arrayDemo; 
反例： 在 main 参数中，使用 String args[]来定义。
```
8.【强制】POJO类中布尔类型的变量，都不要加is前缀，否则部分框架解析会引起序列化错误。 
```java
反例：定义为基本数据类型 Boolean isDeleted 的属性，它的方法也是 isDeleted()，RPC框架在反向解析的时候，
“误以为”对应的属性名称是deleted，导致属性获取不到，进而抛出异常。
```
9.【强制】包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式。
```java
正例：应用工具类包名为com.cifm.micro.util、类名为MessageUtils（此规则参考spring的框架结构）
```
10.【强制】杜绝完全不规范的缩写，避免望文不知义。
```java
反例：AbstractClass“缩写”命名成AbsClass；condition“缩写”命名成 condi，此类随意缩写严重降低了代码的可阅读性。
```
11.【推荐】如果模块、接口、类、方法使用了设计模式，在命名时需体现出具体模式。
```java
说明：将设计模式体现在名字中，有利于阅读者快速理解架构设计理念。
正例：
public class OrderFactory; 
public class LoginProxy; 
public class ResourceObserver;
```
12.【推荐】接口类中的方法和属性不要加任何修饰符号（public 也不要加），保持代码的简洁性，并加上有效的Javadoc注释。尽量不要在接口里定义变量，如果一定要定义变量，肯定是与接口方法相关，并且是整个应用的基础常量。 
```java
正例：接口方法签名 void commit(); 接口基础常量 String COMPANY = "cifm"; 
反例：接口方法定义 public abstract void f(); 
说明：JDK8中接口允许有默认实现，那么这个default方法，是对所有实现类都有价值的默认实现。
```
13.【强制】对于Service和DAO类，基于SOA的理念，暴露出来的服务一定是接口，内部的实现类用Impl的后缀与接口区别。 
```java
正例：CacheServiceImpl实现CacheService接口。
```
14.【参考】枚举类名建议带上Enum后缀，枚举成员名称需要全大写，单词间用下划线隔开。
```java
说明：枚举其实就是特殊的类，域成员均为常量，且构造方法被默认强制是私有。
正例：枚举名字为ProcessStatusEnum的成员名称：SUCCESS / UNKNOWN_REASON。
```
15.【参考】各层命名规约： 
```java
A) Service/DAO层方法命名规约
	1） 获取单个对象的方法用get做前缀。 
	2） 获取多个对象的方法用list做前缀，复数形式结尾如：listObjects。 
	3） 获取统计值的方法用count做前缀。 
	4） 插入的方法用save/insert做前缀。 
	5） 删除的方法用remove/delete做前缀。 
	6） 修改的方法用update做前缀。 
B) 领域模型命名规约 
	1） 数据对象：xxxDO，xxx即为数据表名。 
	2） 数据传输对象：xxxDTO，xxx为业务领域相关的名称。 
	3） 展示对象：xxxVO，xxx一般为网页名称。 
	4） POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。
```
## 常量定义
1.【强制】不允许任何魔法值（即未经预先定义的常量）直接出现在代码中。
```java
反例： String key = "Id#taobao_" + tradeId; cache.put(key, value);
```
2.【强制】在long或者Long赋值时，数值后使用大写的L，不能是小写的l，小写容易跟数字1混淆，造成误解。 
```java
说明： Long a = 2l; 写的是数字的 21，还是Long型的 2?
```
3.【推荐】不要使用一个常量类维护所有常量，要按常量功能进行归类，分开维护。 
```java
说明：大而全的常量类，杂乱无章，使用查找功能才能定位到修改的常量，不利于理解和维护。
正例：缓存相关常量放在类CacheConsts下；系统配置相关常量放在类ConfigConsts下。
```
4.【推荐】常量的复用层次有五层：跨应用共享常量、应用内共享常量、子工程内共享常量、包内共享常量、类内共享常量。
```java
1） 跨应用共享常量：放置在二方库中，通常是client.jar中的constant目录下。 
2） 应用内共享常量：放置在一方库中，通常是子模块中的constant目录下。 
反例：易懂变量也要统一定义成应用内共享常量，两位攻城师在两个类中分别定义了表示“是”的变量： 
类A中： public static final String YES = "yes";
类B中： public static final String YES = "y"; A.YES.equals(B.YES)，预期是true，但实际返回为false，导致线上问题。
3） 子工程内部共享常量：即在当前子工程的constant目录下。 
4） 包内共享常量：即在当前包下单独的constant目录下。 
5） 类内共享常量：直接在类内部private static final定义。
```
5.【推荐】如果变量值仅在一个固定范围内变化用enum类型来定义。 
```java
说明：如果存在名称之外的延伸属性应使用enum类型，下面正例中的数字就是延伸信息，表示一年中的第几个季节。 
正例： 
public enum SeasonEnum {
	SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4); 
	private int seq; 
	SeasonEnum (int seq) { 
		this.seq = seq;
    } 
}
```

## 代码格式
1.【强制】大括号的使用约定。如果是大括号内为空，则简洁地写成{}即可，不需要换行；如果是非空代码块则： 
```java
1） 左大括号前不换行。
2） 左大括号后换行。 
3） 右大括号前换行。 
4） 右大括号后还有else等代码则不换行；表示终止的右大括号后必须换行。
```
2.【强制】左小括号和字符之间不出现空格；同样，右小括号和字符之间也不出现空格；而左大括号前需要空格。详见第5条下方正例提示。 
```java
反例： if (空格a == b空格)
```
3.【强制】if/for/while/switch/do等保留字与括号之间都必须加空格。  

4.【强制】任何二目、三目运算符的左右两边都需要加一个空格。 
```java
说明：运算符包括赋值运算符=、逻辑运算符&&、加减乘除符号等。
```
5.【强制】采用4个空格缩进，禁止使用tab字符。
```java
说明： 如果使用tab缩进，必须设置缩进，必须设置1个tab为4个空格。IDEA设置tab为4个空格时，请勿勾选Use tab character；而在eclipse中，必须勾选insert spaces for tabs。
正例：（涉及1-5点）
public static void main(String[] args) {
    // 缩进4个空格
    String say = "hello";
    // 运算符的左右必须有一个空格
    int flag = 0;
    // 关键词if与括号之间必须有一个空格，括号内的f与左括号，0与右括号不需要空格
    if (flag == 0) {
        System.out.println(say);
    }
    // 左大括号前加空格且不换行；左大括号后换行
    if (flag == 1) {
        System.out.println("world");
    // 右大括号前换行，右大括号后有else，不用换行
    } else {
        System.out.println("ok");
    // 在右大括号后直接结束，则必须换行
    }
} 
```
6.【强制】注释的双斜线与内容之间有且仅一个空格。
```java
正例： 
// 这是示例注释，请注意在双斜线之后有一个空格 
String ygb = new String(); 
```
7.【强制】单行字符数限不超过 120 个，超出需要换行时 个，超出需要换行时 遵循如下原则： 
```java
1） 第二行相对一缩进 4个空格，从第三行开始不再继续缩进参考示例。 
2） 运算符与下文一起换行。 
3） 方法调用的点符号与下文一起换行。 
4） 方法调用中的多个参数需要换行时，在逗号后进行。 
5） 在括号前不要换行，见反例。 
正例：
StringBuffer sb = new StringBuffer();
// 超过120个字符的情况下，换行缩进4个空格，点号和方法名称一起换行
sb.append("zi").append("xin")...
    .append("huang")...
    .append("huang")...
    .append("huang");
反例：
StringBuffer sb = new StringBuffer();
// 超过120个字符的情况下，不要在括号前换行
sb.append("zi").append("xin")...append
    ("huang");
// 参数很多的方法调用可能超过120个字符，不要在逗号前换行
method(args1, args2, args3, ...
    , argsX);
```
8.【强制】方法参数在定义和传入时，多个参数逗号后边必须加空格。 
```java
正例：下例中实参的args1，后边必须要有一个空格。
method(args1, args2, args3);
```
9.【强制】IDE的text file encoding设置为UTF-8; IDE中文件的换行符使用Unix格式，不要使用Windows格式。 

10.【推荐】单个方法的总行数不超过 80 行。 
```java
说明：包括方法签名、结束右大号内代码注释空行回车及任何不可见字符的总行数不超过80行。
正例：代码逻辑分清红花和绿叶，个性和共性，绿叶逻辑单独出来成为额外方法，使主干代码更加清晰；共性逻辑抽取成为共性方法，便于复用和维护。
```
11.【推荐】没有必要增加若干空格来使某一行的字符与上一行对应位置的字符对齐。 
```java
正例：
int one = 1;
long two = 2L;
float three = 3F;
StringBuffer sb = new StringBuffer();
说明：增加sb这个变量，如果需要对齐，则给a、b、c都要增加几个空格，在变量比较多的情况下，是非常累赘的事情。
```
12.【推荐】不同逻辑、不同语义、不同业务的代码之间插入一个空行分隔开来以提升可读性。 
```java
说明：任何情形，没有必要插入多个空行进行隔开。
```

## OOP规约
1.【强制】避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析成本，直接用类名来访问即可。  

2.【强制】所有的覆写方法，必须加@Override注解。 
```java
说明：getObject()与get0bject()的问题。一个是字母的O，一个是数字的0，加@Override可以准确判断是否覆盖成功。另外，如果在抽象类中对方法签名进行修改，其实现类会马上编译报错。
```
3.【强制】相同参数类型，相同业务含义，才可以使用Java的可变参数，避免使用Object。
```java
说明：可变参数必须放置在参数列表的最后。（提倡同学们尽量不用可变参数编程） 
正例：public List<User> listUsers(String type, Long... ids) {...}
```
4.【强制】外部正在调用或者二方库依赖的接口，不允许修改方法签名，避免对接口调用方产生影响。接口过时必须加@Deprecated注解，并清晰地说明采用的新接口或者新服务是什么。  

5.【强制】不能使用过时的类或方法。 
```java
说明：java.net.URLDecoder 中的方法decode(String encodeStr) 这个方法已经过时，应该使用双参数decode(String source, String encode)。接口提供方既然明确是过时接口，那么有义务同时提供新的接口；作为调用方来说，有义务去考证过时方法的新实现是什么。
```
6.【强制】Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用equals。 
```java
正例："test".equals(object); 
反例：object.equals("test"); 
说明：推荐使用java.util.Objects#equals（JDK7引入的工具类）
```
7.【强制】所有的相同类型的包装类对象之间值的比较，全部使用equals方法比较。 
```java
说明：对于 Integer var = ? 在-128至 127范围内的赋值， Integer 对象是在IntegerCache.cache产生，会复用已有对象，这个区间内的 Integer 值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑，推荐使用equals方法进行判断。
```
8.关于基本数据类型与包装数据类型的使用标准如下：
```java
1） 【强制】所有的POJO类属性必须使用包装数据类型。 
2） 【强制】RPC方法的返回值和参数必须使用包装数据类型。 
3） 【推荐】所有的局部变量使用基本数据类型。 
说明：POJO类属性没有初值是提醒使用者在需要使用时，必须自己显式地进行赋值，任何NPE问题，或者入库检查，都由使用者来保证。 
正例：数据库的查询结果可能是null，因为自动拆箱，用基本数据类型接收有NPE风险。 
反例：比如显示成交总额涨跌情况，即正负x%，x为基本数据类型，调用的RPC服务，调用不成功时，返回的是默认值，页面显示为0%，这是不合理的，应该显示成中划线。所以包装数据类型的null值，能够表示额外的信息，如：远程调用失败，异常退出。
```
9.【强制】定义DO/DTO/VO等POJO类时，不要设定任何属性默认值。 
```java
反例：POJO类的gmtCreate默认值为 new Date()，但是这个属性在数据提取时并没有置入具体值，在更新其它字段时又附带更新了此字段，导致创建时间被修改成当前时间。
```
10.【强制】序列化类新增属性时，请不要修改serialVersionUID字段，避免反序列失败；如果完全不兼容升级，避免反序列化混乱，那么请修改serialVersionUID值。 
```java
说明：注意serialVersionUID不一致会抛出序列化运行时异常。
```
11.【强制】构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在init方法中。  

12.【强制】POJO类必须写toString方法。使用IDE中的工具：source> generate toString时，如果继承了另一个POJO类，注意在前面加一下super.toString。 
```java
说明：在方法执行抛出异常时，可以直接调用POJO的toString()方法打印其属性值，便于排查问题。
```
13.【强制】禁止在 POJO类中，同时存在对应属性 xxx的 isXxx()和 getXxx()方法。
```java
说明： 框架在调用属性 xxx 的提取方法时，并不能确定哪个一是被优先调用到。
```
14.【推荐】使用索引访问用String的split方法得到的数组时，需做最后一个分隔符后有无内容的检查，否则会有抛IndexOutOfBoundsException的风险。
```java
说明：
String str = "a,b,c,,";
String[] ary = str.split(",");
// 预期大于3，结果是3
System.out.println(ary.length);
```
15.【推荐】当一个类有多个构造方法，或者多个同名方法，这些方法应该按顺序放置在一起，便于阅读，此条规则优先于第16条规则。  

16.【推荐】 类内方法定义的顺序依次是：公有方法或保护方法 > 私有方法 > getter/setter方法。
```java
说明：公有方法是类的调用者和维护者最关心的方法，首屏展示最好；保护方法虽然只是子类关心，也可能是“模板设计模式”下的核心方法；而私有方法外部一般不需要特别关心，是一个黑盒实现；因为承载的信息价值较低，所有Service和DAO的getter/setter方法放在类体最后。
```
17.【推荐】setter方法中，参数名称与类成员变量名称一致，this.成员名 = 参数名。在getter/setter方法中，不要增加业务逻辑，增加排查问题的难度。 
```java
反例：
public Integer getData() {
    if (condition) {
    	return this.data + 100;
    } else {
    	return this.data - 100;
    }
}
```
18.【推荐】循环体内，字符串的连接方式，使用StringBuilder的append方法进行扩展。 
```java
说明：下例中，反编译出的字节码文件显示每次循环都会new出一个StringBuilder对象，然后进行append操作，最后通过toString方法返回String对象，造成内存资源浪费。
反例：
String str = "start";
for (int i = 0; i < 100; i++) {
	str = str + "hello";
}
```
19.【推荐】final可以声明类、成员变量、方法、以及本地变量，下列情况使用final关键字：
```java
1） 不允许被继承的类，如：String类。 
2） 不允许修改引用的域对象。 
3） 不允许被重写的方法，如：POJO类的setter方法。
4） 不允许运行过程中重新赋值的局部变量。 
5） 避免上下文重复使用一个变量，使用final描述可以强制重新定义一个变量，方便更好地进行重构。
```
20.【推荐】慎用Object的clone方法来拷贝对象。 
```java
说明：对象的clone方法默认是浅拷贝，若想实现深拷贝需要重写clone方法实现域对象的深度遍历式拷贝。
```
21.【推荐】类成员与方法访问控制从严： 
```java
1） 如果不允许外部直接通过 new 来创建对象，那么构造方法必须是 private 。 
2） 工具类不允许有 public 或 default 构造方法。 
3） 类非 static 成员变量并且与子类共享，必须是 protected 。 
4） 类非 static 成员变量并且仅在本类使用，必须是 private 。 
5） 类 static 成员变量如果仅在本类使用，必须是 private。 
6） 若是 static 成员变量，考虑是否为 final 。 
7） 类成员方法只供类内部调用，必须是 private 。 
8） 类成员方法只对继承类公开，那么限制为 protected 。 
说明：任何类、方法、参数、变量，严控访问范围。过于宽泛的访问范围，不利于模块解耦。
思考：如果是一个 private 的方法，想删除就删除，可是一个 public 的service成员方法或成员变量，删除一下，不得手心冒点汗吗？变量像自己的小孩，尽量在自己的视线内，变量作用域太大，无限制的到处跑，那么你会担心的。
```

## 注释规约
1.【强制】类、类属性、类方法的注释必须使用Javadoc规范，使用/**内容*/格式，不得使用// xxx方式。
```java
说明：在IDE编辑窗口中，Javadoc方式会提示相关注释，生成Javadoc可以正确输出相应注释；在IDE中，工程调用方法时，不进入方法即可悬浮提示方法、参数、返回值的意义，提高阅读效率。
```
2.【强制】所有的抽象方法（包括接口中的方法）必须要用Javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能。 
```java
说明：对子类的实现要求，或者调用注意事项，请一并说明。
```
3.【强制】所有的类都必须添加创建者和创建日期。  

4.【强制】方法内部单行注释，在被注释语句上方另起一行，使用//注释。方法内部多行注释使用/* */注释，注意与代码对齐。  

5.【强制】所有的枚举类型字段必须要有注释，说明每个数据项的用途。  

6.【推荐】与其“半吊子”英文来注释，不如用中文注释把问题说清楚。专有名词与关键字保持英文原文即可。
```java
反例：“TCP连接超时”解释成“传输控制协议连接超时”，理解反而费脑筋。
```
7.【推荐】代码修改的同时，注释也要进行相应的修改，尤其是参数、返回值、异常、核心逻辑等的修改。
```java
说明：代码与注释更新不同步，就像路网与导航软件更新不同步一样，如果导航软件严重滞后，就失去了导航的意义。
```
8.【参考】谨慎注释掉代码。在上方详细说明，而不是简单地注释掉。如果无用，则删除。 
```java
说明：代码被注释掉有两种可能性：
	1）后续会恢复此段代码逻辑。
	2）永久不用。前者如果没有备注信息，难以知晓注释动机。后者建议直接删掉（代码仓库保存了历史代码）。
```
9.【参考】对于注释的要求：第一、能够准确反应设计思想和代码逻辑；第二、能够描述业务含义，使别的程序员能够迅速了解到代码背后的信息。完全没有注释的大段代码对于阅读者形同天书，注释是给自己看的，即使隔很长时间，也能清晰理解当时的思路；注释也是给继任者看的，使其能够快速接替自己的工作。  

10.【参考】好的命名、代码结构是自解释的，注释力求精简准确、表达到位。避免出现注释的一个极端：过多过滥的注释，代码的逻辑一旦修改，修改注释是相当大的负担。 
```java
反例：
// put elephant into fridge
put(elephant, fridge);
方法名put，加上两个有意义的变量名elephant和fridge，已经说明了这是在干什么，语义清晰的代码不需要额外的注释。
```
11.【参考】特殊注释标记，请注明标记人与标记时间。注意及时处理这些标记，通过标记扫描，经常清理此类标记。线上故障有时候就是来源于这些标记处的代码。 
```java
1） 待办事宜（TODO）:（ 标记人，标记时间，[预计处理时间]） 表示需要实现，但目前还未实现的功能。这实际上是一个Javadoc的标签，目前的Javadoc还没有实现，但已经被广泛使用。只能应用于类，接口和方法（因为它是一个Javadoc标签）。 
2） 错误，不能工作（FIXME）:（标记人，标记时间，[预计处理时间]） 在注释中用FIXME标记某代码是错误的，而且不能工作，需要及时纠正的情况。
```

## 日志规约
1.【强制】应用中不可直接使用日志系统（Log4j、Logback）中的API，而应依赖使用日志框架SLF4J中的API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一。
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
private static final Logger logger = LoggerFactory.getLogger(Abc.class);
```
2.【强制】日志文件至少保存15天，因为有些异常具备以“周”为频次发生的特点。   
    
3.【强制】应用中的扩展日志（如打点、临时监控、访问日志等）命名方式：
```java
appName_logType_logName.log。
logType:日志类型，如stats/monitor/access等；logName:日志描述。这种命名的好处：通过文件名就可知道日志文件属于什么应用，什么类型，什么目的，也有利于归类查找。 正例：mppserver应用中单独监控时区转换异常，如： mppserver_monitor_timeZoneConvert.log 说明：推荐对日志进行分类，如将错误日志和业务日志分开存放，便于开发人员查看，也便于通过日志对系统进行及时监控。
```
4.【强制】对trace/debug/info级别的日志输出，必须使用条件输出形式或者使用占位符的方式。 
```java
说明：logger.debug("Processing trade with id: " + id + " and symbol: " + symbol); 如果日志级别是warn，上述日志不会打印，但是会执行字符串拼接操作，如果symbol是对象，会执行toString()方法，浪费了系统资源，执行了上述操作，最终日志却没有打印。 
正例：（条件）建设采用如下方式
if (logger.isDebugEnabled()) {
	logger.debug("Processing trade with id: " + id + " and symbol: " + symbol);
}
正例：（占位符）
logger.debug("Processing trade with id: {} and symbol : {} ", id, symbol);
```
5.【强制】避免重复打印日志，浪费磁盘空间，务必在log4j.xml中设置additivity=false。
```java
正例：<logger name="com.taobao.dubbo.config" additivity="false">
```
6.【强制】异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么通过关键字throws往上抛出。 
```java
正例：logger.error(各类参数或者对象toString() + "_" + e.getMessage(), e);
```
7.【推荐】谨慎地记录日志。生产环境禁止输出debug日志；有选择地输出info日志；如果使用warn来记录刚上线时的业务行为信息，一定要注意日志输出量的问题，避免把服务器磁盘撑爆，并记得及时删除这些观察日志。 
```java
说明：大量地输出无效日志，不利于系统性能提升，也不利于快速定位错误点。记录日志时请思考：这些日志真的有人看吗？看到这条日志你能做什么？能不能给问题排查带来好处？
```
8.【推荐】可以使用 warnwarn warn日志级别来记录用户输入参数错误的情况，避免投诉时无所适 从。如非必要，请不在此场景打出 errorerror errorerror级别，避免频繁报警。 级别，避免频繁报警。 
```java
说明： 注意日志输出的级别， errorerror errorerror级别只记录系统逻辑出错、异常 级别只记录系统逻辑出错、异常 或者重要的错误信息。 
```
## SQL规约
1.【强制】单表内的条件查询，禁止写SQL脚本，禁止写自定义查询
```java
正例：
// 表示此字段用=号查询
@CriteriaWhere(type = WhereType.EQUAL)
public Integer getStat() {
    return stat;
}
// 表示此字段用between and查询
@CriteriaWhere(type = WhereType.BETWEEN)
public String getBdate() {
    return bdate;
}
// 表示此字段为模糊查询
@CriteriaWhere(type = WhereType.LIKE)
public String getName() {
    return name;
}
public ResultModel shareConfList(@RequestParam(required = false) String type,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String dateTime,
                                 @RequestParam(required = false) String bedate,
                                 HttpServletRequest request) {
    ShareConf shareConf = new ShareConf();
    shareConf.setType(type);
    shareConf.setName(name);
    shareConf.setCtime(bedate);
    DbUtil.dateBetween(shareConf, dateTime, "bdate", "edate");
    // 此处采用封装好的API查询
    return shareConfService.findAll(shareConf, WebTools.pageRequest(request));
}
```
2.【强制】非单表查询，sql脚本禁止拼接，使用占位符
```java
反例：
String name = "张三";
String sql = "select * from ma_user where name = '" + name + "'";
正例：
String name = "张三";
String sql = "select * from ma_user where name = ?";
preparedStatement = connection.prepareStatement(sql);
preparedStatement.setString(1, name);
```
3.【强制】不要使用count(列名)或count(常量)来替代count(*)，count(*)是SQL92定义的标准统计行数的语法，跟数据库无关，跟NULL和非NULL无关。 
```sql
说明：count(*)会统计值为NULL的行，而count(列名)不会统计此列为NULL值的行。
```
4.【强制】count(distinct col) 计算该列除NULL之外的不重复行数，注意 count(distinct col1, col2) 如果其中一列全为NULL，那么即使另一列有不同的值，也返回为0。
5.【强制】当某一列的值全是NULL时，count(col)的返回结果为0，但sum(col)的返回结果为NULL，因此使用sum()时需注意NPE问题。 
```sql
正例：可以使用如下方式来避免sum的NPE问题：
SELECT IF(ISNULL(SUM(g)),0,SUM(g)) FROM table;
```
6.【强制】使用ISNULL()来判断是否为NULL值。 
```sql
说明：NULL与任何值的直接比较都为NULL。 
1） NULL<>NULL的返回结果是NULL，而不是false。 
2） NULL=NULL的返回结果是NULL，而不是true。 
3） NULL<>1的返回结果是NULL，而不是true。
```
7.【强制】 在代码中写分页查询逻辑时，若count为0应直接返回，避免执行后面的分页语句。  

8.【强制】不得使用外键与级联，一切外键概念必须在应用层解决。 
```sql
说明：以学生和成绩的关系为例，学生表中的student_id是主键，那么成绩表中的student_id则为外键。如果更新学生表中的student_id，同时触发成绩表中的student_id更新，即为级联更新。外键与级联更新适用于单机低并发，不适合分布式、高并发集群；级联更新是强阻塞，存在数据库更新风暴的风险；外键影响数据库的插入速度。
```
9.【强制】禁止使用存储过程，存储过程难以调试和扩展，更没有移植性。  

10.【强制】数据订正（特别是删除、修改记录操作）时，要先select，避免出现误删除，确认无误才能执行更新语句。  

11.【推荐】in操作能避免则避免，若实在避免不了，需要仔细评估in后边的集合元素数量，控制在1000个之内。  

