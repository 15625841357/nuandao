package com.support.utils;

import com.iflytek.cloud.speech.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName iflytekUtils
 * @Author 吴俊淇
 * @Date 2020/4/15 11:02
 * @Version 1.0
 **/
@Slf4j
public class iflytekUtils {
    private static final String APPID = "5e6729f7";
    private static StringBuffer mResult = new StringBuffer();
    private boolean mIsEndOfSpeech = false;
    private static  String s;

    public static String getS() {
        return s;
    }

    public static void setS(String s) {
        iflytekUtils.s = s;
    }

    public iflytekUtils(FileInputStream fileInputStream) {
        SpeechUtility.createUtility("appid=" + APPID);
        Recognize(fileInputStream);
    }

    private void Recognize(FileInputStream fileInputStream) {
        if (SpeechRecognizer.getRecognizer() == null)
            SpeechRecognizer.createRecognizer();
        mIsEndOfSpeech = false;
        RecognizePcmfileByte(fileInputStream);
    }


    /**
     * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
     */
    public void RecognizePcmfileByte(FileInputStream fileInputStream) {
        SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        //写音频流时，文件是应用层已有的，不必再保存
//		recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//				"./iat_test.pcm");
        recognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain");
        recognizer.startListening(recListener);

        FileInputStream fis = null;
        final byte[] buffer = new byte[64 * 1024];
        try {
            fis = fileInputStream;
//            fis = new FileInputStream(new File("./test.pcm"));
            if (0 == fis.available()) {
                mResult.append("no audio avaible!");
                recognizer.cancel();
            } else {
                int lenRead = buffer.length;
                while (buffer.length == lenRead && !mIsEndOfSpeech) {
                    lenRead = fis.read(buffer);
                    recognizer.writeAudio(buffer, 0, lenRead);
                }//end of while
                recognizer.stopListening();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of try-catch-finally

    }

    /**
     * 听写监听器
     */
    private RecognizerListener recListener = new RecognizerListener() {
        public void onBeginOfSpeech() {
            log.info("onBeginOfSpeech enter");
            log.info("*************开始录音*************");
        }

        public void onEndOfSpeech() {
            log.info("onEndOfSpeech enter");
            mIsEndOfSpeech = true;
        }

        public void onVolumeChanged(int volume) {
            log.info("onVolumeChanged enter");
            if (volume > 0)
                log.info("*************音量值:" + volume + "*************");

        }

        public void onResult(RecognizerResult result, boolean islast) {
            log.info("onResult enter");
            String ss=result.getResultString();
            mResult.append(ss);
            setS(ss);
            if (islast) {
                log.info("识别结果为:" + mResult.toString());
                mIsEndOfSpeech = true;
                mResult.delete(0, mResult.length());
                waitupLoop();
            }
        }

        public void onError(SpeechError error) {
            mIsEndOfSpeech = true;
            log.info("*************" + error.getErrorCode()
                    + "*************");
            waitupLoop();
        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            log.info("onEvent enter");
        }

    };

    private void waitupLoop() {
        synchronized (this) {
            System.out.println("sagiskagsa");
            iflytekUtils.this.notify();
        }
    }
}
