package eus.aimar.healthy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eus.aimar.healthy.DAO.ElementsDB;
import eus.aimar.healthy.DAO.PacienteDAO;
import eus.aimar.healthy.Model.Paciente;

public class PacienteRepositorio {
    PacienteDAO pacienteDAO;
    Executor executor = Executors.newSingleThreadExecutor();


    public PacienteRepositorio(Application application){
        pacienteDAO = ElementsDB.obtainInstance(application).getPacienteDAO();

    }
    public LiveData<List<Paciente>> get(){ return pacienteDAO.getPacientes(); }
    public  LiveData<Paciente> getPaciente(int id){return pacienteDAO.getPaciente(id);}
    public LiveData<List<Paciente>> getPacientee(int id){ return pacienteDAO.getPacientee(id); }
    public LiveData<Paciente> getPacienteByMedico(int medico){ return pacienteDAO.getPacienteByMedico(medico); }

    public void insert(Paciente paciente) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pacienteDAO.insert(paciente);
            }
        });
    }

    public void delete(Paciente paciente) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pacienteDAO.delete(paciente);
            }
        });
    }
    public void deletePaciente(int id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pacienteDAO.deletePaciente(id);
            }
        });
    }
    public void update(Paciente paciente) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pacienteDAO.update(paciente);
            }
        });
    }
}
