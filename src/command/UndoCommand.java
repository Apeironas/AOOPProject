package command;

// 3. üyenin Memento sistemi hazır olduğunda bu sınıf güncellenerek ona delege edilecek
public class UndoCommand implements Command {

    public UndoCommand() {}

    @Override
    public void execute() {
        // TODO: 3. üye Memento sistemini buraya bağlayacak
    }

    @Override
    public void undo() {}
}
