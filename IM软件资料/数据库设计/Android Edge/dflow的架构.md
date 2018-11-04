还有几个没有弄清楚:
1.Helper 减轻presenter的逻辑，用来处理送服务器中回调后的数据，以及数据库的信息
2.Dbsource
3.Card ---> model是用来请求的时候组装数据，从而返回一个card
4.MessageRepository
5.MessageCenter	dispatcher派遣者


DataSource 设置了全局数据的监听器，当Helper获取到数据的时候，我们对其产生监听。 包括了本地和网络。比如login，
DbDataSource 对于基于 本地数据操作 对DataSource的进一步封装

MessageCenter 定于消息的契约，因为消息有很多种，我们需要对其进行判别，来进行分发给不同helper，进行一个操作。
MessageDispatcher  对于MessageCenter的实现


AppDatabase 记录了数据库的基本信息
BaseDbModel 我们APP中的基础的一个BaseDbModel，基础了数据库框架DbFlow中的基础，同时定义类我们需要的方法，对于AppDatabase 的实现
同时分装了一些数据的操作



abstract class BaseDbRepository<Data extends BaseDbModel<Data>> implements DbDataSource<Data>,DbHelper.ChangedListener<Data>,
        QueryTransaction.QueryResultListCallback<Data> 一个是查询对于数据的回调，一个是对helper的回调通知，
作为一个仓库具有的功能: 对数据的判别，对数据的存储查询，以及通知对方

<Data extends BaseDbModel<Data>> implements DbDataSource<Data>仓库储存的数据必须是 数据可以储存的Bean,
DbHelper.ChangedListener<Data> 同时我们要实现监听数据的变化， 
QueryTransaction.QueryResultListCallback<Data>， 查询回调接口
命令子类必须实现  isRequired(Data data) 是否是我要求得数据

DbHelper 用来监听数据变化，同时也是调用数据对于数据库的操作

MessageRepository extends BaseDbRepository<Message> implements MessageDataSource 实现特殊的仓库要求，以及父类的要去

