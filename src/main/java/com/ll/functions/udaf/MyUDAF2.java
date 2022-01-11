package com.ll.functions.udaf;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;

/**
 * @Author lin_li
 * @Date 2022/1/11 16:51
 */
public class MyUDAF2 extends UDAF {

    public static class JWord {
        private String word;
    }

    public static class MyUDAF2Evaluator implements UDAFEvaluator {
        JWord jWord;

        public MyUDAF2Evaluator() {
            super();
            jWord = new JWord();
            init();
        }

        @Override
        public void init() {
            jWord.word = "";
        }

        public boolean iterate(String str) {
            if (str != null) {
                jWord.word += str;
            }
            return true;
        }


        public JWord terminatePartial() {
            // combiner
            return jWord.word.length() == 0 ? null : jWord;
        }


        public boolean merge(JWord jWord1) {
            if (jWord1 != null) {
                this.jWord.word= jWord1.word;
            }
            return true;
        }

        public String terminate() {
            return jWord.word.length() == 0 ? null : jWord.word;
        }
    }
}

