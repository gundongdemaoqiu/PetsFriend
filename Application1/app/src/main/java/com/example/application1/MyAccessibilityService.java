package com.example.application1;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {

//    @Override
//    public void onAccessibilityEvent(AccessibilityEvent event) {
//    }
//
//    @Override
//    public void onInterrupt() {
//    }
@Override
protected void onServiceConnected() {
    super.onServiceConnected();
}

    /**
     * 通过系统监听窗口变化的回调,sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        //根据时间回调类型进行处理.
        switch (eventType) {
            //通知栏变化时
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                //当窗口状态发生改变时.
                break;
        }
    }

    /**
     * 中断服务时的回调.
     */
    @Override
    public void onInterrupt() {

    }

    /**
     * 查找拥有特定焦点类型的控件
     *
     * @param focus
     * @return
     */
    @Override
    public AccessibilityNodeInfo findFocus(int focus) {
        return super.findFocus(focus);
    }

    /**
     * 如果配置能够获取窗口内容,则会返回当前活动窗口的根结点
     *
     * @return
     */
    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        return super.getRootInActiveWindow();
    }


    /**
     * 获取系统服务
     *
     * @param name
     * @return
     */
    @Override
    public Object getSystemService(String name) {
        return super.getSystemService(name);
    }

    /**
     * 如果允许服务监听按键操作，该方法是按键事件的回调，需要注意，这个过程发生了系统处理按键事件之前
     *
     * @param event
     * @return
     */
    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }
    public void setAccessibilityServiceInfo() {
        String[] packageNames = {"com.tencent.mm"};
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        //相应时间的类型,(长安,点击,滑动)
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        //反馈给用户的类型,这里是语音
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        //过滤的包名
        accessibilityServiceInfo.packageNames = packageNames;
        setServiceInfo(accessibilityServiceInfo);
    }


}