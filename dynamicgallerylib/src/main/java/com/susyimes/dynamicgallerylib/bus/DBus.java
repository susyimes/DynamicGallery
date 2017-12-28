//package com.susyimes.dynamicgallerylib.bus;
//
//import rx.Observable;
//import rx.subjects.PublishSubject;
//import rx.subjects.SerializedSubject;
//import rx.subjects.Subject;
//
///**
// *  2016/6/21 0021.
// */
//public class RxBusDefault {
//    private static volatile RxBusDefault defaultInstance;
//    // 主题
//    private final Subject bus;
//
//    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
//    public RxBusDefault() {
//        bus = new SerializedSubject<>(PublishSubject.create());
//    }
//
//    // 单例RxBusDefault
//    public static RxBusDefault getDefault() {
//        RxBusDefault dBus = defaultInstance;
//        if (defaultInstance == null) {
//            synchronized (RxBusDefault.class) {
//                dBus = defaultInstance;
//                if (defaultInstance == null) {
//                    dBus = new RxBusDefault();
//                    defaultInstance = dBus;
//                }
//            }
//        }
//        return dBus;
//    }
//
//    // 提供了一个新的事件
//    public void post(Object o) {
//        bus.onNext(o);
//    }
//
//    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
//    public <T> Observable<T> toObserverable(Class<T> eventType) {
//        return bus.ofType(eventType);
//
//    }
//}