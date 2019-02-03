package com.example.factory.middleware;

import com.example.common.factory.data.DataSource;
import com.example.netKit.db.User;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 建立对增删查改处理与监听，并反馈回给Dbdatabase，可有结构图分析
 *
 * 0. 建立单例模式，以防容器的添加的紊乱
 * 1. 设置数据存储容器
 * 2. 设置数据监听者的添加与移除
 * 3. 设置数据的通知与存储
 * 4. 设置数据的存储与删除
 *
 * @author KevinLeak
 */
public class DbHelper {


    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper() {
    }




    public static void save(Class<User> userClass, User user) {

    }

    public static User getFromNet(String userId) {

        return null;
    }



    // 设置监听器容器
    /**
     *
     *
     * 通过不同类型的model，来确定应该通过哪些回调来更新
     * Class<?> 为不同类型model
     * Set<DataChangeListener>> 需要回调的集合
     *
     * 不同的listener 来自不同的仓库，有不同的presenter来取
     *
     */
    private final Map<Class<?>, Set<DataChangeListener>> changedListeners = new HashMap<>();



    // 设置添加、删除监听器
    /**
     * 规定我们的键必须BaseModel 类型的
     */
    public static <model extends BaseModel>  void
        addChangeListener(Class<model> cls, DataChangeListener<model> listener){
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners == null){
            dataChangeListeners = new HashSet<>();
            instance.changedListeners.put(cls, dataChangeListeners);
        }
        dataChangeListeners.add(listener);
    }


    public static <model extends BaseModel>  void
    removeChangeListener(Class<model> cls, DataChangeListener<model> listener){
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners != null){
            dataChangeListeners.remove(listener);
        }
    }


    /**
     *获取监听者对应的列表
     */
    public <model extends BaseModel> Set<DataChangeListener> getChangedListeners(Class<model> cls) {
        if (changedListeners.containsKey(cls)){
            return changedListeners.get(cls);
        }
        return null;
    }

    // 设置监听通知
    private <model extends BaseModel> void notifySave(Class<model> clsType, model... models){
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0){
            for (DataChangeListener<model> listener: dataChangeListeners){
                listener.onDataSave(models);
            }
        }

        // 处理特殊情况，例如当前的present 并不是需要作出改变的

    }

    private <model extends BaseModel> void notifyDelete(Class<model> clsType, model... models){
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0){
            for (DataChangeListener<model> listener: dataChangeListeners){
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
    interface DataChangeListener<Data extends BaseModel>{
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }
}
