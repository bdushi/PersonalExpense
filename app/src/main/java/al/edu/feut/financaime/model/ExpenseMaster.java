package al.edu.feut.financaime.model;
import java.util.List;

//ignore
public class ExpenseMaster {
    private String total;
    private List<Expense> expenses;

    public ExpenseMaster(List<Expense> expenses) {
        this.expenses = expenses;
    }
    public ExpenseMaster() {
    }
}
