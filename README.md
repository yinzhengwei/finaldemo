# finaldemo
项目通用架构和基础库的封装
本项目是为了能快速搭建一套项目的基础框架而开发的，里面包含了各种基础功能的使用，如有不足，可以自行完善


1、基础类（MVVM简单用法）

所有的Activity需要继承自BaseActivity并实现对应的接口，所有的Fragment需要继承自BaseFragment并实现对应的接口；
其次泛型参数一ViewBinding来源自对应布局文件中layout的声明自动生成；参数二ViewModel每个界面对应一个，并且改
ViewModel需要继承自BaseViewModel并实现对应的方法


2、权限

项目已经添加了依赖库permissiongranted，具体使用请参照：https://github.com/yinzhengwei/permissiongranted

3、网络请求

项目已经添加了依赖库networklibrary，直接在对应的ViewModel中的loadData方法中调用cn.com.finaldemo.network.BaseRequest
里的方法即可，自定义添加header参数。
更多使用具体请参照：https://github.com/yinzhengwei/permissiongranted


4、协程

项目中launchUi.kt类中是协程的基本用法，launch{} / launchAsyn{}表示启动异步任务，launchUi{}切换到主线程

5、导航条

项目中MagicIndicatorTabView.kt类中接收viewpager、标题数据、选种颜色等值


6、下拉刷新

swiperefreshlayout库已在gradle中添加，可以直接在布局文件中使用


7、状态栏

直接通过StatusUtil类调用setUseStatusBarColor和setSystemStatus方法，项目中baseActivity中有设置


8、eventbus

eventbus库已在gradle中添加，可以直接使用


9、万能适配器

BaseRecyclerViewAdapterHelper库已在gradle中添加，可以直接通过自定义adapter配合RecyclerView使用


10、OAID库

项目libs/miit_mdid_1.0.10.aar是获取OAID的资源库，MiitHelper是调用方式,项目中通过DeviceIdUtil封装了获取方式，可以直接
通过deviceID和deviceType获取设备ID和ID类型。如果OAID获取失败则会依次获取IMEI、DEVICEID、UUID并且会将结果通过MD5加密


11、sharePreference缓存

项目中GmPlayPreferenceUtil是对SharePreference的封装，可以直接调用里面各种数据类型的保存、获取方法


12、图片加载

GlideLoadUtil.kt是对Glide的封装，支持imageVIew、textView、ViewGroup的内容图片加载，支持圆角和GIF方法等


13、toast

ToastUtil类是对toast的封装，直接直接调用showToast显示toast提示


14、网络状态判断

NetUtil类是对网络状态的封装，可以直接调用获取网络状态是否可用
