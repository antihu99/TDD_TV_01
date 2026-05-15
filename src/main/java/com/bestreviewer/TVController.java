/**
 * Copyright 2020 by Samsung Electronics, Inc.,
 *
 * This software is the confidential and proprietary information
 * of Samsung Electronics, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Samsung.
 */

package com.bestreviewer;

public class TVController {
    private Tuner tuner;
    private String processingCH;

    public TVController(Tuner tuner) {
        this.tuner = tuner;
        processingCH = "";
    }

    public void pushButton(remoteKey key) {
        switch (key) {
            case KEY_1:
                processingCH += key.toString();
                break;

            case KEY_OK:
                setTunerCh();
                break;
        }
    }

    private void setTunerCh() {
        //로그는 테스트의 결과가 절대 아닙니다. 로그가 있는 것을 테스트로 간주하지 마시기 바랍니다.
        System.out.println("현재 설정하는 채널 : " + processingCH);
        //tuner.setCH(processingCH);
    }

}
