# AppleMusicProject
音乐播放器
本人通过学习慕课网上的学习视频(https://www.imooc.com/learn/1104)，在慕课网LGD_Sunday老师的课程指导下完成了苹果音乐的开发，苹果音乐播放器的技术点如下：
1 、利用Realm数据库存储音乐资源和用户信息；
①Realm是直接在移动设备中运行的数据库，不只可以在Android中使用，还可以在苹果手机、Windows Phone上面使用。
②Realm并没有基于任何的现成数据库，它是一个重新研发的数据库。
③Realm数据库不需要使用sql语句就可以操控它。
④同时支持Java，Object-C，swift，Javascript，.net等开发语言，支持IOS，Android，JavaScript，Xamarin等平台。
Realm数据模型：1。一个模型表示一张表，模型中的字段表示表中的列；2.模型需要继承RealmObject类；3.模型实时、自动更新；
*Realm实例使用引用计数的方式，生成的Realm实例需要手动Close        
2 、读取本地json数据；
3、通过Glide加载网络图片；
4、自定义View；
5、RecyclerView的使用；
6、通过Service和Notification播放音乐；
7、glide-transformation实现图片的高斯模糊效果；
8、单例模式的使用；
9、解决RecyclerView与ScrollView嵌套使用RecycleView的高度不能正常显示的问题；
10、RegexUtils验证手机号码是否合法；
11、Activity切入切出动画（缩放、旋转、平移）；
