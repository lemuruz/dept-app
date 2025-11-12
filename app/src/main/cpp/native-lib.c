#include <jni.h>
#include <android/log.h>
//#include "com_example_dept_app_data_Debts"

JNIEXPORT jint JNICALL
Java_com_example_dept_1app_ViewModel_calculateNetDebt(
        JNIEnv* env,
        jobject thiz,
        jint youOwe,
        jint friendOwes) {
    return friendOwes - youOwe;
}


JNIEXPORT void JNICALL
Java_com_example_dept_1app_ViewModel_addDebt(
        JNIEnv* env,
        jobject thiz,
        jstring jfriendName,
        jstring jdate,
        jstring jdescription,
        jdouble jamount,
        jstring jtype) {
    // Convert Java strings to C strings
    const char  *name = (*env)->GetStringUTFChars(env, jfriendName, 0);
    const char *date = (*env)->GetStringUTFChars(env, jdate, 0);
    const char *desc = (*env)->GetStringUTFChars(env, jdescription, 0);
    const char *type = (*env)->GetStringUTFChars(env, jtype, 0);


    jclass cls = (*env)->GetObjectClass(env, thiz);
    jmethodID insertMethod = (*env)->GetMethodID(env, cls,
                                                 "insertDebtFromNative",
                                                 "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DLjava/lang/String;)V");
    (*env)->CallVoidMethod(env, thiz, insertMethod, jfriendName,jdate, jdescription, jamount, jtype);

    (*env)->ReleaseStringUTFChars(env, jdate, date);
    (*env)->ReleaseStringUTFChars(env, jdescription, desc);
    (*env)->ReleaseStringUTFChars(env, jtype, type);


}