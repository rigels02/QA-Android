package org.rb.qaandro.storage.simple;

/**
 *
 * @author raitis
 */
public class QA {


    private String question;


    private String answer;

    public QA() {
    }


    public QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QA{" + "question=" + question + ", answer=" + answer + '}';
    }


}
