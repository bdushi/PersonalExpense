package al.bruno.personal.expense

import al.bruno.financaime.dependency.injection.InjectionProvider.providerSettingsInjection
import al.bruno.personal.expense.databinding.FragmentSettingsBinding
import al.bruno.personal.expense.model.Settings
import al.bruno.personal.expense.observer.Observer
import al.bruno.personal.expense.observer.Subject
import al.bruno.personal.expense.util.ViewModelProviderFactory
import al.bruno.personal.expense.view.model.SettingsViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsFragment : Fragment(), Subject<Settings> {
    private val disposable : CompositeDisposable = CompositeDisposable()
    private var observer = ArrayList<Observer<Settings>>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentSettingsBinding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        val factory = ViewModelProviderFactory(SettingsViewModel(providerSettingsInjection(context!!)!!))
        val settings = Settings(1)
        disposable.add(ViewModelProviders
                .of(this, factory)[SettingsViewModel::class.java]
                .settings(1)
                .subscribeOn(Schedulers.io())
                .subscribe({ iSettings ->
                    fragmentSettingsBinding.settings = iSettings
                    iSettings.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                            disposable
                                    .add(ViewModelProviders
                                            .of(this@SettingsFragment)[SettingsViewModel::class.java]
                                            .insert(iSettings)
                                            .subscribeOn(Schedulers.io()).subscribe({

                                            }, {

                                            }))
                        }
                    })
                }, {
                    fragmentSettingsBinding.settings = settings
                }))
        settings.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Toast.makeText(context, settings.toString(), Toast.LENGTH_SHORT).show()
                disposable
                        .add(ViewModelProviders
                                .of(this@SettingsFragment)[SettingsViewModel::class.java]
                                .insert(settings)
                                .subscribeOn(Schedulers.io()).subscribe({

                                }, {

                                }))
            }
        })
        return fragmentSettingsBinding.root;
    }
    override fun registerObserver(o: Observer<Settings>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeObserver(o: Observer<Settings>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notifyObserver(t: Settings) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
