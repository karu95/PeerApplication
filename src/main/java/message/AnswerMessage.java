package message;

import com.peerapplication.model.Answer;

public class AnswerMessage extends Message {
    private Answer answer;

    public AnswerMessage() {
        super("AnswerMessage");
    }

    public AnswerMessage(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
