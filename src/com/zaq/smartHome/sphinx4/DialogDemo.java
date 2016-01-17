/*
 * Copyright 2013 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package com.zaq.smartHome.sphinx4;

import java.util.HashMap;
import java.util.Map;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;


public class DialogDemo {

    private static final String ACOUSTIC_MODEL =
        "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH =
        "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
    private static final String LANGUAGE_MODEL =
        "resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin";

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath(ACOUSTIC_MODEL);
        configuration.setDictionaryPath(DICTIONARY_PATH);
        configuration.setLanguageModelPath(LANGUAGE_MODEL);;

        LiveSpeechRecognizer liveSpeechRecognizer=new LiveSpeechRecognizer(configuration);
        liveSpeechRecognizer.startRecognition(true);
        while (true) {
            System.out.println("listening.............");

            String utterance = liveSpeechRecognizer.getResult().getHypothesis();
            System.out.println("###########:"+utterance);
            if (utterance.startsWith("exit"))
                break;

        }

        liveSpeechRecognizer.stopRecognition();
    }
}
