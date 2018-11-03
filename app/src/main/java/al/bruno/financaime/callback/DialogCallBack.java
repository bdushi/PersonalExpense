package al.bruno.financaime.callback;

import al.bruno.financaime.adapter.CustomSpinnerAdapter;
import al.bruno.financaime.model.Budget;

public interface DialogCallBack {
    void onClickInsert(Budget budget);
    void onClickUpdate(Budget budget);
}
