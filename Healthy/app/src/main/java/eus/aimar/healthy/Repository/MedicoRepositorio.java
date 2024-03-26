package eus.aimar.healthy.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eus.aimar.healthy.DAO.ElementsDB;
import eus.aimar.healthy.DAO.MedicoDAO;
import eus.aimar.healthy.Model.Medico;

public class MedicoRepositorio {
   MedicoDAO medicoDAO;
    Executor executor = Executors.newSingleThreadExecutor();


    public MedicoRepositorio(Application application){
        medicoDAO = ElementsDB.obtainInstance(application).getMedicoDAO();
    }
    public LiveData<List<Medico>> getMedicosByUsername(String username) {
          return medicoDAO.getMedicosByUsername(username);

    }
    public LiveData<List<Medico>> get(){ return medicoDAO.getMedicos(); }

    public void insert(Medico medico) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                medicoDAO.insert(medico);
            }
        });
    }

    public void delete(Medico medico) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                medicoDAO.delete(medico);
            }
        });
    }

    public void update(Medico medico) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                medicoDAO.update(medico);
            }
        });
    }
}
