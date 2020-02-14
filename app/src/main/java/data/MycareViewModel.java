package data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MycareViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<UserEntity>> muserentity;

    public MycareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }



    public LiveData<String> getText() {
        return mText;
    }
}