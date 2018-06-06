package al.edu.feut.financaime.callback;

import al.edu.feut.financaime.model.Budget;

public interface DialogCallBack {
    void onClickInsert(Budget budget);
    void onClickUpdate(Budget budget);
}
