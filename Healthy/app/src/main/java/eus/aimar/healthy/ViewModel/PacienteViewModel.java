package eus.aimar.healthy.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import eus.aimar.healthy.Model.Paciente;
import eus.aimar.healthy.Repository.PacienteRepositorio;

public class PacienteViewModel extends AndroidViewModel {
    PacienteRepositorio pacientesRepo;
    String id;
    MutableLiveData<Paciente> pacienteSelected = new MutableLiveData<>();

    public PacienteViewModel(@NonNull Application application){
        super(application);
        pacientesRepo = new PacienteRepositorio(application);

    }
    public LiveData<List<Paciente>> getPacientee(int id){ return pacientesRepo.getPacientee( id); }

    public LiveData<List<Paciente>> get(){ return pacientesRepo.get(); }

    public void add(Paciente paciente) {
        pacientesRepo.insert(paciente);
    }

    void delete(Paciente paciente) {
        pacientesRepo.delete(paciente);
    }
   public void deletePaciente(int id){pacientesRepo.deletePaciente(id);}

  public  void update(Paciente paciente) {
        pacientesRepo.update(paciente);
    }

    void select(Paciente paciente) {
        pacienteSelected.setValue(paciente);
    }
    public String getLoggedInPacienteID() {
        return id;
    }
    public void  setLoggedInPacienteID(String ID) {
         id=ID;
    }
    public MutableLiveData<Paciente> selected() {
        return pacienteSelected;
    }

}
