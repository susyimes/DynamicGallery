package com.susyimes.dynamicgallerylib.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 *  2016/6/21 0021.
 */
public class DBus {
    private static volatile DBus defaultInstance;
    // 主题
    private final Subject bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public DBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例DBus
    public static DBus getDefault() {
        DBus dBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (DBus.class) {
                dBus = defaultInstance;
                if (defaultInstance == null) {
                    dBus = new DBus();
                    defaultInstance = dBus;
                }
            }
        }
        return dBus;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);

    }
}