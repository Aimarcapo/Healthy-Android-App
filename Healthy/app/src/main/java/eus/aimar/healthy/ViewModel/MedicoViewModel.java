package eus.aimar.healthy.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.Repository.MedicoRepositorio;

public class MedicoViewModel extends AndroidViewModel {
    MedicoRepositorio medicosRepo;
    //MutableLiveData<List<Client>> listClientsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Medico> medicoSelected = new MutableLiveData<>();

    public MedicoViewModel(@NonNull Application application){
        super(application);
        medicosRepo = new MedicoRepositorio(application);
       
    }

    /*public List<Medico> getMedicosByUsername(String username) {


            return medicosRepo.getMedicosByUsername(username);


    }*/
    public LiveData<List<Medico>> getMedicosByUsername(String username) {
        return  medicosRepo.getMedicosByUsername(username);

    }

    //public  LiveData<Medico> getUser(String medico){ return medicosRepo.getUser(medico); }
    public LiveData<List<Medico>> get(){ return medicosRepo.get(); }


    public void add(Medico medico) {
        medicosRepo.insert(medico);
    }

    void delete(Medico medico) {
        medicosRepo.delete(medico);
    }

    void update(Medico medico) {
        medicosRepo.update(medico);
    }

    void select(Medico medico) {
        medicoSelected.setValue(medico);
    }

    MutableLiveData<Medico> selected() {
        return medicoSelected;
    }
    private Medico loggedInMedico;

    public Medico getLoggedInMedico() {
        return loggedInMedico;
    }

    public void setLoggedInMedico(Medico medico) {
        loggedInMedico = medico;
    }
}
