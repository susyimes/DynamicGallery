package com.susyimes.dynamicgallerylib.bus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Susyimes on 2017/12/1.
 *
 */



public class RxBusDefault {

        private static volatile RxBusDefault defaultInstance;

        private final FlowableProcessor<Object> bus;


        public RxBusDefault() {
            bus = PublishProcessor.create().toSerialized();
        }


        public static RxBusDefault getDefault() {
            RxBusDefault dBus = defaultInstance;
            if (defaultInstance == null) {
                synchronized (RxBusDefault.class) {
                    dBus = defaultInstance;
                    if (defaultInstance == null) {
                        dBus = new RxBusDefault();
                        defaultInstance = dBus;
                    }
                }
            }
            return dBus;
        }


        public void post(Object o) {
            bus.onNext(o);
        }


        public <T> Flowable<T> toObserverable(Class<T> eventType) {
            return bus.ofType(eventType);

        }

// Simple useage
// Post in somewhere
// RxBusDefault.getDefault().post(new DefaultAction("test"));
// Create  Disposable
// Consumer do Action1 in somewhere
//        private void initRxBus() {
//            RxBusDefault.getDefault().toObserverable(DefaultAction.class).subscribe(new Consumer<DefaultAction>() {
//                @Override
//                public void accept(DefaultAction defaultAction) throws Exception {
//                    if (defaultAction.getAction().equals("test")){
//                        DebugLog.d("test","this action work");
//                    }
//                }
//            });
//        }

}
