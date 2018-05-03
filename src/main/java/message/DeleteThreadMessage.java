package message;

import com.peerapplication.model.DeletedThread;

public class DeleteThreadMessage extends Message {

    private DeletedThread deletedThread;

    public DeleteThreadMessage() {
        super("DeleteThreadMessage");
    }


    public DeletedThread getDeletedThread() {
        return deletedThread;
    }

    public void setDeletedThread(DeletedThread deletedThread) {
        this.deletedThread = deletedThread;
    }
}
