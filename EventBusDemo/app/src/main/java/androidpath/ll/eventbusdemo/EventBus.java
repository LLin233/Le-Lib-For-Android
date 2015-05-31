package androidpath.ll.eventbusdemo;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Le on 2015/5/30.
 */
public class EventBus {

    private Handler mHandler;

    private static EventBus eventBus = new EventBus();
    // Methods store in here
    private static Map<Class, CopyOnWriteArrayList<SubscribeMethod>> mSubscribeMethodsByEventType = new HashMap<Class, CopyOnWriteArrayList<SubscribeMethod>>();

    private static ThreadLocal<PostingThread> mPostingThread = new ThreadLocal<PostingThread>() {
        @Override
        public PostingThread get() {
            return new PostingThread();
        }
    };

    public static EventBus getInstatnce() {
        return eventBus;
    }

    private EventBus() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void register(Object subscriber) {

        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        /**
         * Scan all methods
         */
        for (Method method : methods) {
            String methodName = method.getName();
            /**
             * look for Methods start with "onEvent"
             */
            if (methodName.startsWith("onEvent")) {
                SubscribeMethod subscribeMethod = null;
                String threadMode = methodName.substring("onEvent".length());
                ThreadMode mode = ThreadMode.UI;

                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == 1) {
                    Class<?> eventType = parameterTypes[0];

                    synchronized (this) {

                        if (mSubscribeMethodsByEventType.containsKey(eventType)) {
                            subscribeMethods = mSubscribeMethodsByEventType
                                    .get(eventType);
                        } else {
                            subscribeMethods = new CopyOnWriteArrayList<SubscribeMethod>();
                            mSubscribeMethodsByEventType.put(eventType,
                                    subscribeMethods);
                        }
                    }

                    if (threadMode.equals("Async")) {
                        mode = ThreadMode.Async;
                    }
                    // (method£¬mode£¬subscriber) -> SubscribeMethod
                    subscribeMethod = new SubscribeMethod(method, mode,
                            subscriber);
                    subscribeMethods.add(subscribeMethod);
                }
            }

        }
    }

    public void unregister(Object subscriber) {
        Class clazz = subscriber.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        List<SubscribeMethod> subscribeMethods = null;

        for (Method method : methods) {
            String methodName = method.getName();

            if (methodName.startsWith("onEvent")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    synchronized (this) {
                        mSubscribeMethodsByEventType.remove(parameterTypes);
                    }
                }
            }
        }

    }

    public void post(Object eventTypeInstance) {
        // get PostingThread Object in the thread
        PostingThread postingThread = mPostingThread.get();
        postingThread.isMainThread = Looper.getMainLooper() == Looper
                .myLooper();
        //Add event to queue
        List<Object> eventQueue = postingThread.mEventQueue;
        eventQueue.add(eventTypeInstance);
        // prevent thread is called multiple times
        if (postingThread.isPosting) {
            return;
        }
        postingThread.isPosting = true;
        // call events
        while (!eventQueue.isEmpty()) {
            Object eventType = eventQueue.remove(0);
            postEvent(eventType, postingThread);
        }
        postingThread.isPosting = false;

    }

    private void postEvent(final Object eventType, PostingThread postingThread) {
        CopyOnWriteArrayList<SubscribeMethod> subscribeMethods = null;
        synchronized (this) {
            subscribeMethods = mSubscribeMethodsByEventType.get(eventType
                    .getClass());
        }

        for (final SubscribeMethod subscribeMethod : subscribeMethods) {

            if (subscribeMethod.threadMode == ThreadMode.UI) {
                if (postingThread.isMainThread) {
                    invokeMethod(eventType, subscribeMethod);
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(eventType, subscribeMethod);
                        }
                    });
                }
            } else {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        invokeMethod(eventType, subscribeMethod);
                        return null;
                    }
                };

            }

        }

    }

    private void invokeMethod(Object eventType, SubscribeMethod subscribeMethod) {
        try {
            subscribeMethod.method
                    .invoke(subscribeMethod.subscriber, eventType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

enum ThreadMode {
    UI, Async
}

class SubscribeMethod {
    Method method;
    ThreadMode threadMode;
    Object subscriber;

    public SubscribeMethod(Method method, ThreadMode threadMode,
                           Object subscriber) {
        this.method = method;
        this.threadMode = threadMode;
        this.subscriber = subscriber;
    }

}

class PostingThread {
    List<Object> mEventQueue = new ArrayList<Object>();
    boolean isMainThread;
    boolean isPosting;
}
