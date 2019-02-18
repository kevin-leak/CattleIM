package com.example.factory.middleware;

import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.netKit.db.AppDatabase;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Event;
import com.example.netKit.db.Event_Table;
import com.example.netKit.db.User;
import com.example.netKit.model.UserModel;
import com.example.netKit.net.push.ConversationFactory;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 建立对增删查改处理与监听，并反馈回给Dbdatabase，可有结构图分析
 * <p>
 * 0. 建立单例模式，以防容器的添加的紊乱
 * 1. 设置数据存储容器
 * 2. 设置数据监听者的添加与移除
 * 3. 设置数据的通知与存储
 * 4. 设置数据的存储与删除
 *
 * @author KevinLeak
 */
public class DbHelper {

    final static String TAG = "DbHelper";


    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper() {
    }


    /**
     * @param modelClass 指的model类型的class，也指信息表
     * @param models     数据库表的集合
     * @param <Model>    开启一个事务： 数据的实际存储，数据通知
     */
    public static <Model extends BaseModel> void save(final Class<Model> modelClass, final Model... models) {

        if (models == null || models.length == 0)
            return;

        for (Model model : models)
            Log.e(TAG, "save: " + model.toString());
        // 当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        // 提交一个事物
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(modelClass);
                // 保存
                adapter.saveAll(Arrays.asList(models));



                Log.e(TAG, "save: " + " 数据开始存储");
                // 唤起通知
                instance.notifySave(modelClass, models);
            }

        }).build().execute();

    }

    public static User getFromNet(String userId) {

        return null;
    }


    // 设置监听器容器
    /**
     * 通过不同类型的model，来确定应该通过哪些回调来更新
     * Class<?> 为不同类型model
     * Set<DataChangeListener>> 需要回调的集合
     * <p>
     * 不同的listener 来自不同的仓库，有不同的presenter来取
     */
    private final Map<Class<?>, Set<DataChangeListener>> changedListeners = new HashMap<>();


    // 设置添加、删除监听器

    /**
     * 规定我们的键必须BaseModel 类型的
     */
    public static <model extends BaseModel> void addChangeListener(Class<model> cls,
                                                                   DataChangeListener<model> listener) {
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners == null) {
            dataChangeListeners = new HashSet<>();
            instance.changedListeners.put(cls, dataChangeListeners);
        }
        dataChangeListeners.add(listener);
    }


    public static <model extends BaseModel> void removeChangeListener(Class<model> cls,
                                                                      DataChangeListener<model> listener) {
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners != null) {
            dataChangeListeners.remove(listener);
        }
    }


    /**
     * 获取监听者对应的列表
     */
    public <model extends BaseModel> Set<DataChangeListener> getChangedListeners(Class<model> cls) {
        if (changedListeners.containsKey(cls)) {
            return changedListeners.get(cls);
        }
        return null;
    }

    // 设置监听通知
    private <model extends BaseModel> void notifySave(Class<model> clsType, model... models) {
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0) {
            for (DataChangeListener<model> listener : dataChangeListeners) {
                listener.onDataSave(models);
            }
        }

        // 处理特殊情况，例如当前的present 并不是需要作出改变的


        // 确认当前是什么类型的数据
        if (clsType.equals(Conversation.class)) {
            dealEvent(models);
        }

    }

    private <model extends BaseModel> void dealEvent(model[] models) {
        ModelAdapter<Event> eventModelAdapter = FlowManager.getModelAdapter(Event.class);
        Event event = null;
        ArrayList<Event> events = new ArrayList<>();
        for (model model : models) {
            // 形态转化
            Conversation newModel = (Conversation) model;
            //是否已经存在会话
            Event old_event = SQLite.select().from(Event.class)
                    .where(Event_Table.chatId.eq(newModel.chatId))
                    .querySingle();

            // 可能有，但并没有这个chat id
            if (old_event == null){

            }

            if (old_event != null) {
                Log.e(TAG, "dealEvent: " +"会话复用" );
                // 更新event 中简略消息
                old_event.setContent(newModel.getContent());
                // 将未读消息进行更新
                old_event.setUnReadCount(old_event.getUnReadCount() + 1);
                eventModelAdapter.save(old_event);
                event = old_event;
            } else {
                Log.e(TAG, "dealEvent: " +"建立新的会话" );
                // 根据前面我们创建的chatID来建造event
                Event new_event = ConversationFactory.createEvent(((Conversation) model));

                // 将model 与 event 相互绑定
                if (new_event != null){
                    newModel.setEvent(new_event);
                    newModel.save();
                    eventModelAdapter.save(new_event);
                }

                event = new_event;
            }
            if (event != null){
                events.add(event);
            }
        }

        if (events.size() > 0){
            instance.notifySave(Event.class, events.toArray(new Event[0]));
        }
    }



    private <model extends BaseModel> void notifyDelete(Class<model> clsType, model... models) {
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0) {
            for (DataChangeListener<model> listener : dataChangeListeners) {
                listener.onDataDelete(models);
            }
        }

        // 处理特殊情况，例如当前的present 并不是需要作出改变的

    }


    /**
     * 数据更新后，提供回调接口，来通知其他仓库
     * 对于数据的操作：删除，存储（更新），设计数据为数组模式
     * 数据类型为数据model 模式
     */
    @SuppressWarnings({"unused", "unchecked"})
    interface DataChangeListener<Data extends BaseModel> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }
}
