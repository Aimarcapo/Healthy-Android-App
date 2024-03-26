package eus.aimar.healthy.DAO;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eus.aimar.healthy.Model.Medico;
import eus.aimar.healthy.Model.Paciente;

@Database(entities = {Medico.class, Paciente.class}, version = 1, exportSchema = false)
public abstract class ElementsDB extends RoomDatabase  {
    public abstract MedicoDAO getMedicoDAO();
    public abstract PacienteDAO getPacienteDAO();
    private static volatile ElementsDB INSTANCE;

   public static ElementsDB obtainInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElementsDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    ElementsDB.class, "hospital.db")
                            //.allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }




}