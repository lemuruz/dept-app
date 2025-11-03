#include <jni.h>
#include <android/log.h>


JNIEXPORT jint JNICALL
Java_com_example_dept_1app_MainActivity_calculateNetDebt(
        JNIEnv* env,
        jobject thiz,
        jint youOwe,
        jint friendOwes) {
    return friendOwes - youOwe;
}


